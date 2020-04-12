(ns hello-quil.box2d.box
  (:require [quil.core :as q]
            [quil.middleware :as middleware]
            [hello-quil.box2d.util :refer [m->px px->m width height]]
            [clojure.core.matrix :as m])
  (:import (org.jbox2d.dynamics World BodyDef BodyType FixtureDef)
           (org.jbox2d.common Vec2)
           (org.jbox2d.collision.shapes PolygonShape)))

(defn create-body [world x y w h]
  (let [body-def (BodyDef.)
        shape (PolygonShape.)
        fixture (FixtureDef.)]

    (set!  (.-type body-def) BodyType/DYNAMIC)
    (.set (.-position body-def)
          (Vec2. (* x px->m)
                 (* y px->m)))

    (.setAsBox shape
               (/ (* w px->m) 2)
               (/ (* h px->m) 2))

    (set! (.-shape fixture) shape)
    (set! (.-density fixture) 100)
    (set! (.-friction fixture) 0.3)
    (set! (.-restitution fixture) 0.5)

    (doto (.createBody world body-def)
      (.createFixture fixture))))

(defn new [world x y]
  {:entity/x x
   :entity/y y
   :entity/w 20
   :entity/h 20
   :entity/body (create-body world x y 20 20)})

(defn delete! [{:entity/keys [body]} world]
  (.destroyBody world body))

(defn out-of-bounds? [{:entity/keys [body]}]
  (> (* (.-y (.getPosition body)) m->px) height))

(defn draw [{:entity/keys [w h body]}]
  (q/fill 50)
  (q/push-matrix)
  (q/translate (* (.-x (.getPosition body)) m->px)
               (* (.-y (.getPosition body)) m->px))
  (q/rotate (* (.getAngle body) -1))
  (q/rect 0 0 w h)
  (q/pop-matrix))
