(ns hello-quil.acc
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [clojure.core.matrix :as m]
            [hello-quil.liquid :as liquid]
            [hello-quil.entity :as entity]))

(def width 600)
(def height 600)
(def window (atom (m/array [width height])))

(def liquid-shape
  {:x 0
   :y (/ height 2)
   :w width
   :h (/ width 2)})

(def center (m/array [(/ width 2) (/ height 2)]))

(defn mouse-moved [state {:keys [x y]}]
  (assoc state :mouse (m/array [x y])))

(defn setup []
  (q/frame-rate 30)
  {:time 0
   :mouse (m/array [0 0])
   :entities (->> (repeat (partial entity/new width height))
                  (take 10)
                  (mapv #(%)))})


(def wind (m/array [0.02 0]))

(def wind2 (m/array [0.8 0]))

(def gravity (m/array [0 0.9]))
(def friction -0.03)
(def liquid-c 0.3)

(defn apply-extra-wind
  [entity]
  (if (q/mouse-pressed?)
    (entity/apply-force entity wind2)
    entity))

(def idle-v (m/array [0 0]))

(defn normalize [v]
  (if (= v idle-v)
    v
    (m/normalise v)))

(defn calc-drag-force [{:entity/keys [velocity] :as entity}]
  (let [m-sqr (m/magnitude-squared velocity)
        speed (m/mul liquid-c m-sqr)
        direction (m/mul (normalize velocity) -1)]
    (m/mul speed direction)))

(defn apply-water [{:entity/keys [velocity] :as entity}]
  (if (liquid/contains? liquid-shape entity)
    (entity/apply-force entity (calc-drag-force entity))
    entity))

(defn update-entity
  ""
  [{:keys [mouse time]} {:entity/keys [velocity] :as entity}]
  (-> entity
      (entity/apply-force gravity)
      (apply-water)
      ;; (entity/apply-force wind)
      (entity/apply-force (m/mul (normalize velocity) friction))
      ;; (apply-extra-wind)
      (entity/step time mouse window)))

(defn update-state [{:keys [] :as state}]
  (-> state
      (update :entities #(mapv (partial update-entity state) %))
      (update :time + (/ (q/current-frame-rate) 1000))))

(defn draw-state [{:keys [entities]}]
  (q/background 240)
  (doseq [entity entities]
    (entity/draw entity))
  (liquid/draw liquid-shape @window))

(q/defsketch template-quil
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :mouse-moved mouse-moved
  :middleware [middleware/fun-mode])
