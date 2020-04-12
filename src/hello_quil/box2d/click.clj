(ns hello-quil.box2d.click
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [hello-quil.box2d.box :as box]
            [clojure.core.matrix :as m]))

(def width 600)
(def height 300)
(def center (m/array [(/ width 2) (/ height 2)]))

(defn setup []
  (q/frame-rate 60)
  {:boxes [(box/new 40 40)]})

(defn add-pressed-box [state]
  (if (q/mouse-pressed?)
    (update state :boxes conj (box/new (q/mouse-x) (q/mouse-y)))
    state))

(defn update-state [state]
  (add-pressed-box state))

(defn draw-state [{:keys [boxes]}]
  (q/background 240)
  (doseq [box boxes]
    (box/draw box)))

(q/defsketch template-quil
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [middleware/fun-mode])
