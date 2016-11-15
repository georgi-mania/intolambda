(ns intolambda.ui-components
  (:require
   [cljsjs.material]
   [intolambda.utils :refer [load-element display-grid]]))

;; Snackbar

;; (defn show-snackbar [parent-id text]
;;   (let [parent (.getElementById js/document parent-id)]
;;     (.showSnackbar (.MaterialSnackbar parent) text)))

;; (defn snackbar-component [text]
;;   [:div.mdl-snackbar.mdl-js-snackbar
;;    {:id "snackbar-component"}
;;    [:div.mdl-snackbar__text text]
;;    [:button.mdl-snackbar__action {:type "button"}]])

(defn- build-dialog-action-button [{:keys [action-name action-fn]}]
  [:button.mdl-button.mdl-color--accent
   {:type "button"
    :key action-name
    :on-click #(action-fn)}
    action-name])

(defn show-modal [id]
  (let [modal (load-element id)]
    (set! (.-display (.-style modal)) "block")))

(defn close-modal [id]
  (let [modal (load-element id)]
    (set! (.-display (.-style modal)) "none")))

(defn build-modal [id title content actions]
  [:div.page-content.modal
   {:id id}
   [:div.modal-content
    [:h5.dark-primary-color.text-primary-color title]
    [:div content]
    [:div
     (display-grid "" actions build-dialog-action-button)]]])
