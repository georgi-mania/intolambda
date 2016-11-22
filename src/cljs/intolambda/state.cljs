(ns intolambda.state
  (:require
   [reagent.core :as r]))

(def init-state
  {:menu-item-selected "Home"
   :contrib-selection {}
   :initial-challenge ""})

(def app-state (r/atom init-state))

(defn get-app-state []
  app-state)

(defn update-app-state [keys value]
  (if (== 1 (count keys))
    (swap! app-state assoc (first keys) value)
    (swap! app-state assoc-in keys value)))

(defn read-app-state
  ([]
   app-state)
  ([key]
   (key @app-state)))
