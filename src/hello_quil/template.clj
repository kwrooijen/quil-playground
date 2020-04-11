(ns hello-quil.template
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [clojure.core.matrix :as m]))

(def width 300)
(def height 300)
(def center (m/array [(/ width 2) (/ height 2)]))

(defn setup []
  (q/frame-rate 60)
  {})

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 240))

(q/defsketch template-quil
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [middleware/fun-mode])
