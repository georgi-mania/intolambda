(ns intolambda.dom-utils
  (:require
   [reagent.core :as r]))

(def upgrade-dom (.. js/componentHandler -upgradeDom))

(defn mdl [& children]
  (r/create-class
   {:display-name "mdl-wrapper"
    :component-did-mount (fn [] (upgrade-dom))
    :component-did-update (fn [] (upgrade-dom))
    :reagent-render (fn [& children] (into [:div] children))}))

(defn render-main [main-fn doc-id]
  (r/render-component [main-fn]
                      (.getElementById js/document doc-id)))

(defn retrieve-element [element-id]
  (r/render-component (.getElementById js/document element-id)))
