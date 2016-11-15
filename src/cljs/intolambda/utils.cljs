(ns intolambda.utils
  (:require
   [cljsjs.material]
   [clojure.string :refer [starts-with?]]))

(defn- is-link? [text]
  (or
   (starts-with? text "www")
   (starts-with? text "http")
   (starts-with? text "/")))

(defn get-in-ext-link [text]
  (if (is-link? text)
    (if (starts-with? text "www")
      (str "http://" text)
      text)
    (str "#" text)))

(defn- build-link [text link]
  [:a.mdl-color-text--accent {:href link} text])

(defn- get-text-or-link [text]
  (if (is-link? text)
    (build-link text (str (get-in-ext-link text)))
    text))

(defn icon-text-line [icon text]
  [:div.mdl-mini-footer__link-list
   [:li
    [:i.material-icons icon]]
   [:li
    (get-text-or-link text)]])

(defn icon-link [icon text link]
  [:div.mdl-mini-footer__link-list
   [:li
    [:i.material-icons icon]]
   [:li
    (build-link text link)]])

(defn build-checkbox [name label on-click-fn & args]
  [:div
   [:label.mdl-checkbox.mdl-js-checkbox
    {:for name
     ; :on-click on-click
     :key (hash name)}
    [:input.mdl-checkbox__input
     {:id name
      :type "checkbox"
      :on-click #(if args
                   (on-click-fn name (first args))
                   (on-click-fn name))}]
    [:span.mdl-checkbox__label.mdl-color-text--primary label]]])

(defn build-speaker-box [speaker]
  [:div.mdl-cell.mdl-cell--4-col.mdl-cell--2-col-tablet.mdl-cell--2-col-phone
   {:key (hash-coll speaker)}
   [:img.avatar
    {:src (str "/images/speakers/" (:avatar speaker))
     :style {:border-radius "50%"}}]
   [:h4.mdl-color-text--secondary (:name speaker)]
   [:div
    {:style {:text-align "center"}}
    (:desc speaker)]])

(defn build-sponsor-box [sponsor]
  [:div.mdl-cell.mdl-cell--4-col.mdl-cell--2-col-tablet.mdl-cell--2-col-phone
   {:key (hash-coll sponsor)}
   [:img.avatar
    {:src (str "/images/sponsors/" (:avatar sponsor))
     :on-click #(set! (.-location js/document) (:link sponsor))
     :style {:cursor "pointer"}}]])

(defn build-contributor-box [contributor cont-fn]
  [:div.mdl-cell.mdl-cell--middle.mdl-cell--3-col.mdl-cell--2-col-tablet.mdl-cell--2-col-phone
   {:key (hash-coll contributor)}
   (build-checkbox contributor contributor cont-fn)])

(defn display-grid [title item-list f]
  "Builds a grid with the given name
   Each grid item is obtained by applying f to an item from item-list."
                                        ;  (js/alert (str " display grid: " args))
  [:div.page-content
   [:h3.dark-primary-color.text-primary-color title]
   [:div.content-grid.mdl-grid.center-component.mdl-color-text--primary
    (map #(f %) item-list)]])

(defn load-element [id]
  (.getElementById js/document id))

(defn show-element [id]
  (let [element (load-element id)]
    (set! (.-hidden element) false)))
