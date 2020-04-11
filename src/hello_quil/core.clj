(ns hello-quil.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def width 300)
(def height 300)

(def ball {:ball/location {:x (/ width 2) :y (/ height 2)}
           :ball/speed {:x 2.5 :y -2}})

(defn move-ball [ball]
  (update ball :ball/location
          #(merge-with + % (:ball/speed ball))))

(defn draw-ball [{:ball/keys [location]}]
  (q/ellipse (:x location)
             (:y location) 50 50))

(defn bounce [{:ball/keys [location] :as ball}]
  (let [x (:x location)
        y (:y location)]
    (cond
      (or (> x width) (< x 0))
      (update-in ball [:ball/speed :x] * -1)

      (or (> y height) (< y 0))
      (update-in ball [:ball/speed :y] * -1)
      :else ball)))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :rgb)
  {:state/ball ball})

(defn update-state [state]
  (-> state
      (update :state/ball move-ball)
      (update :state/ball bounce)))

(defn draw-state [state]
  (q/background 240)
  (q/fill 255 0 0)
  (draw-ball (:state/ball state)))


(q/defsketch hello-quil
  :size [width height]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
