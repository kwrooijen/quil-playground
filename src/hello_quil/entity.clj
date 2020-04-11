(ns hello-quil.entity
  (:require
   [quil.core :as q]
   [clojure.core.matrix.random :as r]
   [clojure.core.matrix :as m :refer [mget mset]]))

(defn new [window]
  {:entity/location     (m/div @window 2)
   :entity/velocity     (m/array [0 0])
   :entity/acceleration (m/array [0 0])
   :entity/radius       50
   :entity.ref/window   window})

(defn update-bounds [location window]
  (cond
    (> (mget location 0) (mget window 0))
    (mset location 0 0)

    (< (mget location 0) 0)
    (mset location 0 (mget window 0))

    (> (mget location 1) (mget window 1))
    (mset location 1 0)

    (< (mget location 1) 0)
    (mset location 1 (mget window 1))

    :else
    location))

(defn limit [v m]
  (if (> (m/magnitude-squared v) (* m m))
    (-> (m/normalise v)
        (m/mul m))
    v))

(defn step [{:entity/keys [velocity acceleration]
             :entity.ref/keys [window]
             :as entity}
            time]
  (-> entity
      ;; (assoc :entity/acceleration (r/sample-normal 2))
      ;; (update :entity/acceleration  mset 1 0)
      (update :entity/acceleration  mset 0 (- (q/noise time) 0.5))
      (update :entity/acceleration  mset 1 (- (q/noise (+ 1000 time)) 0.5))
      (update :entity/acceleration  m/mul 5)
      (update :entity/velocity     m/add acceleration)
      (update :entity/velocity     limit 5)
      (update :entity/location     m/add velocity)
      (update :entity/location     update-bounds @window)))

(defn draw [{:entity/keys [location radius]}]
  (q/fill 255 0 0)
  (q/ellipse (mget location 0) (mget location 1)
             radius radius))
