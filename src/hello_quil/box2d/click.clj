(ns hello-quil.box2d.click
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [hello-quil.box2d.box :as box]
            [clojure.core.matrix :as m]
            [hello-quil.box2d.util :refer [width height]])
  (:import (org.jbox2d.dynamics World)
           (org.jbox2d.common Vec2)))


(defn setup []
  (q/frame-rate 60)

  (let [world (World. (Vec2. 0 10))]
    (.setWarmStarting world true)
    (.setContinuousPhysics world true)
    {:boxes [(box/new world 40 40)]
     :world world}))

(defn add-pressed-box [state]
  (if (q/mouse-pressed?)
    (update state :boxes conj (box/new (:world state) (q/mouse-x) (q/mouse-y)))
    state))

(defn bodies [world]
  (let [bodies (volatile! [])]
    (loop [body (.getBodyList world)]
      (if body
        (do
          (vswap! bodies conj body)
          (recur (.getNext body)))
        @bodies))))

(defn filter-bodies [state]
  (let [[to-be-removed remaining] (split-with box/out-of-bounds? (:boxes state))]
    (doseq [box to-be-removed] (box/delete! box (:world state)))
    (assoc state :boxes remaining)))

(defn update-state [{:keys [world] :as state}]
  (.step world (/ 1000 (/ 1000 60)) 10 8)
  (.clearForces world)
  (-> state
      (filter-bodies)
      (add-pressed-box)))

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
