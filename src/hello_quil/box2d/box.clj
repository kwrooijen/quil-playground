(ns hello-quil.box2d.box
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [clojure.core.matrix :as m]))

(defn new [x y]
  {:entity/x x
   :entity/y y
   :entity/w 50
   :entity/h 50})

(defn draw [{:entity/keys [x y w h]}]
  (q/fill 50)
  (q/rect x y w h))
