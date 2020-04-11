(ns hello-quil.acc
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [clojure.core.matrix :as m]
            [hello-quil.entity :as entity]))

(def width 300)
(def height 300)
(def window (atom (m/array [width height])))

(def center (m/array [(/ width 2) (/ height 2)]))

(defn setup []
  (q/frame-rate 30)
  {:time 0
   :ball (entity/new window)})

(defn update-state [{:keys [time] :as state}]
  (-> state
      (update :ball entity/step time)
      (update :time + (/ (q/current-frame-rate) 1000))))

(defn draw-state [state]
  (q/background 240)
  (entity/draw (:ball state)))

(q/defsketch template-quil
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [middleware/fun-mode])
