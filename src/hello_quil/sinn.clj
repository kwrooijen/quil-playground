(ns hello-quil.sinn
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [clojure.core.matrix :as m]
            [clojure.core.matrix.random :as r]))

(def width 300)
(def height 300)

(defn new-entity []
  {:velocity (m/div (m/normalise (r/sample-normal 2)) 20)
   :angle (r/sample-normal 2)
   :amplitude (m/array [(- (rand-int width) (/ width 2))
                        (- (rand-int height) (/ height 2))])})

(defn step-entity [entity]
  (update entity :angle m/add (:velocity entity)))

(defn draw-entity [{:keys [amplitude angle]}]
  (let [x (* (Math/sin (m/mget angle 0)) (m/mget amplitude 0))
        y (* (Math/sin (m/mget angle 1)) (m/mget amplitude 1))]
    (q/line 0 0 x y)
    (q/ellipse x y 30 30)))

(defn setup []
  (q/frame-rate 60)
  {:entities (->> (repeat new-entity)
                  (take 10)
                  (mapv #(%)))})

(defn update-state [state]
  (update state :entities #(mapv step-entity %)))

(defn draw-state [{:keys [entities]}]
  (q/translate (/ width 2) (/ height 2))
  (q/background 240)
  (q/fill 255 0 0)
  (q/stroke 0)
  (doseq [entity entities]
    (draw-entity entity)))

(q/defsketch template-quil
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [middleware/fun-mode])
