(ns hello-quil.entity
  (:require
   [quil.core :as q]
   [clojure.core.matrix.random :as r]
   [clojure.core.matrix :as m :refer [mget mset]]))

(defn new [w h]
  (let [mass (inc (inc (rand-int 5)))]
    {:entity/location     (m/array [(rand-int w) (rand-int h)])
     :entity/velocity     (m/array [0 0])
     :entity/acceleration (m/array [0 0])
     :entity/radius       (* mass 10)
     :entity/mass         mass}))

(defn update-bounds [{:entity/keys [location velocity] :as entity} window]
  (cond
    (> (mget location 0) (mget window 0))
    (-> entity
        (update :entity/velocity mset 0 (* -1 (mget velocity 0)))
        (update :entity/location mset 0 (mget window 0)))

    (< (mget location 0) 0)
    (-> entity
        (update :entity/velocity mset 0 (* -1 (mget velocity 0)))
        (update :entity/location mset 0 0))

    (> (mget location 1) (mget window 1))
    (-> entity
        (update :entity/velocity mset 1 (* -1 (mget velocity 1)))
        (update :entity/location mset 1 (mget window 1)))

    (< (mget location 1) 0)
    (-> entity
        (update :entity/velocity mset 1 (* -1 (mget velocity 1)))
        (update :entity/location mset 1 0))

    :else
    entity))

(defn limit [v m]
  (if (> (m/magnitude-squared v) (* m m))
    (-> (m/normalise v)
        (m/mul m))
    v))

(defn apply-force [{:entity/keys [mass] :as entity} f]
  (update entity :entity/acceleration m/add (m/div f mass)))

(defn apply-net-force [entity f]
  (update entity :entity/acceleration m/add f))

(defn step [{:entity/keys [velocity acceleration location]
             :as entity}
            time
            mouse
            window]
  (-> entity
      (update :entity/velocity m/add acceleration)
      (update :entity/velocity limit 10)
      (update :entity/location m/add velocity)
      (update-bounds @window)
      (update :entity/acceleration m/mul 0)))

(defn draw [{:entity/keys [location radius]}]
  (q/fill 255 0 0)
  (q/ellipse (mget location 0) (mget location 1)
             radius radius))
