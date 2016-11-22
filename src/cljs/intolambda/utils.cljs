(ns intolambda.utils
  (:require
   [cljsjs.material]
   [clojure.string :refer [starts-with? replace]])
  (:require-macros
   [hiccups.core :refer [html]]))

(declare navigate)
(declare nav-and-hack)

(defn load-element [id]
  (.getElementById js/document id))

(defn show-element
  ([id] (show-element id false))
  ([id val]
   (let [element (load-element id)]
     (set! (.-hidden element) val))))

(defn- is-checked [id]
  (.-checked (load-element id)))

(defn set-checkbox-flag [id flag]
  (let [element (load-element id)
        was-checked (.-checked element)]
    (if-not (= flag was-checked)
      (.click element))))

(defn check-checkbox [id]
  (let [element (load-element id)
        was-checked (.-checked element)]
    (if-not was-checked (.click element))
    ;; (set! (.-checked element) true)
    ;; (and element (set! (.-checked element) true))
    ))

(defn- is-link? [text]
  (if text
    (or
     (starts-with? text "www")
     (starts-with? text "http")
     (starts-with? text "/"))))

(defn is-section? [text]
  (if text
    (starts-with? text "#")))

(defn- make-classy-div [text & class]
  [:div {:class (first class)} text])

(defn get-in-ext-link [text & class]
  (if (is-link? text)
    (if (starts-with? text "www")
      (str "http://" text)
      text)
    (str "#" text)))

(defn- build-link [text link]
  [:a.mdl-color-text--accent {:href link} text])

(defn- get-text-or-link [text class]
  (if (is-link? text)
    (build-link text (str (get-in-ext-link text class)))
    (make-classy-div text class)))

(defn icon-text-line
  ([icon text class]
   [:div.mdl-mini-footer__link-list.with-padding
    [:li]
    [:i.material-icons icon]
    [:li]
    (get-text-or-link "" class)
    (get-text-or-link text nil)
    (get-text-or-link "" class)])
  ([icon text]
   (icon-text-line icon text nil)))

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
  [:div.mdl-cell.mdl-cell--3-col.mdl-cell--4-col-tablet.mdl-cell--4-col-phone
   {:key (hash-coll speaker)
    :on-click (nav-and-hack (:link speaker)
                            "speaker"
                            check-checkbox)
    :style {:cursor "pointer"}}
   [:img.avatar
    (and (:avatar speaker)
         {:src (str "images/speakers/" (:avatar speaker))
          :style {:border-radius "50%"}})]
   [:h4.mdl-color-text--secondary (:name speaker)]
   [:div
    {:style {:text-align "center"}}
    (:desc speaker)]])

(defn build-sponsor-box [sponsor]
  [:div.mdl-cell.mdl-cell--3-col.mdl-cell--3-col-tablet.mdl-cell--4-col-phone
   {:key (hash-coll sponsor)
    :on-click (nav-and-hack (:link sponsor)
                            "sponsor"
                            check-checkbox)
    :style {:cursor "pointer"}}
   [:img.avatar
    (and (:avatar sponsor)
         {:src (str "images/sponsors/" (:avatar sponsor))})]])

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

(defn navigate [nav-link]
  (let [section-fn #(do
                      (show-element (replace nav-link #"#" ""))
                      (set! (.-location js/document) nav-link))
        link-fn #(set! (.-location js/document) nav-link)]
    (cond (is-link? nav-link) link-fn
          (is-section? nav-link) section-fn)))

(defn nav-and-hack [nav-link id f]
  #(do ((navigate nav-link))
       (f id)))
