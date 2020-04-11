(ns hello-quil.liquid
  (:require
   [clojure.core.matrix :as m]
   [quil.core :as q]))

(defn contains?
  [{:keys [x y w h]} {:entity/keys [location]}]
  (and (< x       (m/mget location 0))
       (< y       (m/mget location 1))
       (> (+ x w) (m/mget location 0))
       (> (+ y h) (m/mget location 1))))

(defn draw [{:keys [x y w h]} window]
  (q/no-stroke)
  (q/fill (q/color 225 0 0 120))
  (q/rect x y w h))
