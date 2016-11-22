(ns intolambda.header
  (:require
   [cljsjs.material]
   [reagent.core :refer [atom]]
   [intolambda.state :as st]
   [intolambda.utils :refer [get-in-ext-link load-element]]))

(enable-console-print!)

(def menu [["Home" "home"]
           ["The challenge" "the-challenge"]
           ["Register" "register"]
           ["Speakers" "speakers"]
           ["Sponsors" "sponsors"]
           ["Contact" "footer"]
                                        ;["Blog" "www.linktoblog.eu"]
           ])

(def reload-atom (atom 0))

(defn- update-state [new-item-selected]
  (do
    (swap! reload-atom inc)
    (st/update-app-state [:menu-item-selected] new-item-selected)))

(defn- layout-fn [selected?]
  (if selected?
    (vector :a.mdl-button.mdl-js-button.accent-color.primary-text-color)
    (vector :a.mdl-button.mdl-js-button.text-primary-color)))

(defn- build-button [name link]
  (let [selected? (== (st/read-app-state :menu-item-selected) name)
        parsed-link {:href (get-in-ext-link link)
                     :on-click #(update-state name)
                     :key (hash name)}]
    (conj (layout-fn selected?) parsed-link name)))

(defn close-drawer []
  (let [drawer (load-element "drawer")]
    (set! (.-className drawer) "mdl-layout__drawer dark-primary-color")))

(defn- navigation-component [menu]
  (doall
   (map #(build-button (first %) (last %)) menu)))

(defn header-component []
  @reload-atom
  [:header.mdl-layout__header.mdl-layout__header.dark-primary-color
   {:id "header" :on-click close-drawer}
   [:div.mdl-layout__header-row.dark-primary-color
    [:div.mdl-layout-spacer]
    (navigation-component menu)]])

(defn drawer-component []
  @reload-atom
  [:div.intolambda-drawer.mdl-layout__drawer.dark-primary-color
   {:id "drawer" :on-click #(close-drawer)}
   (navigation-component menu)])
