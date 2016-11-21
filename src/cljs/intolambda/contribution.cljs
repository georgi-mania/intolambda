(ns intolambda.contribution
  (:require
   [cljsjs.material]
   [reagent.core :refer [atom]]
   [intolambda.utils :refer [load-element display-grid build-contributor-box show-element]]
   [intolambda.form-utils :refer [build-form-for-email]]))

(defonce selections (atom {}))

(defn- is-checked [id]
  (.-checked (load-element id)))

(defn- merge-selections []
  (let [sels (map #(if (last %)
                     (-> % first name))
                  @selections)]
    (keep (fn[x] (-> x nil? not) x) sels)))

(defn- update-extra-info [id]
  (let [extra-info (load-element id)
        roles (clojure.string/join "," (merge-selections))]
    (set! (.-value extra-info) roles)))

(defn toggle-checkbox [id]
  (let [was-checked (is-checked id)]
    (if (-> id nil? not)
      (do
        (swap! selections assoc (keyword id) was-checked)
        (update-extra-info "extra-info")))))

;; (defn- confirmation-popup []
;;   (ui/build-modal conf-id
;;                   "Thank you!"
;;                   "We will get back to you very soon!"
;;                   [{:action-name "OK" :action-fn #(ui/close-modal conf-id)}]))

(defn- clear-selections []
  (reset! selections {}))

(defn contribution-component [roles contributors close-fn]
  (let [form-params {:action "http://formspree.io/icslabcrew@gmail.com"
                     :method "POST"
                     :on-submit #(clear-selections)}]
    [:div
     (display-grid
      "contribute"
      roles
      #(build-contributor-box % toggle-checkbox))
     [build-form-for-email form-params contributors nil close-fn]]))
