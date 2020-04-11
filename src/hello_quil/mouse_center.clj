(ns hello-quil.mouse-center
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [clojure.core.matrix :as m]))

(def width 300)
(def height 300)

(def center (m/array [(/ width 2) (/ height 2)]))

(def mouse (atom (m/array [0 0])))

(defn mouse-bar-length
  "calculate the magnitude of the mouse pointer (length of origin vs position).
  Then draw a red bar from the origin with the length of the mouse's magnitude."
  []
  (q/fill 255 0 0)
  (q/rect 0 0 (m/magnitude @mouse) 20))

(defn set-static-mouse-length [length]
  (swap! mouse m/normalise)
  (swap! mouse m/mul length))

(defn setup []
  (q/frame-rate 60)
  {:mouse/x 0 :mouse/y 0
   :center/x (/ width 2) :center/y (/ width 2)})

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 240)
  (q/translate (/ width 2)
               (/ height 2))
  ;; (q/line 0 0 300 300)
  (q/line 0 0 (first @mouse) (second @mouse))

  (mouse-bar-length))

(defn mouse-moved [_ {:keys [x y]}]
  (reset! mouse (m/array [x y]))
  (swap! mouse m/sub center)
  ;; (swap! mouse v/mult (v/vector 0.5 0.5))

  )

(q/defsketch template-quil
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :mouse-moved mouse-moved
  :middleware [middleware/fun-mode])

(defn limit [v m]
  (cond-> v
    (> (m/sqrt v) (* m m))
    (-> (m/normalise v)
        (m/mul max))))
