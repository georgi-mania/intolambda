(ns intolambda.app
  (:require
   [cljsjs.material]
   [reagent.core :as r]
   [intolambda.sections :as s]
   [intolambda.header :as h]
   [intolambda.footer :as f]
   [intolambda.socials :as soc]
   [intolambda.data :as d]
   [intolambda.http-utils :as http]
   [intolambda.dom-utils :as dom]
   [intolambda.state :as st]))

(defn mdl [& children]
  (r/create-class
   {:display-name "mdl-wrapper"
    :component-did-mount (fn [] (dom/upgrade-dom))
    :component-did-update (fn [] (dom/upgrade-dom))
    :reagent-render (fn [& children] (into [:div] children))}))

(defn- load-initial-challenge [link]
  (let [callback (fn [response]
                  ; (js/alert (str "OK:" response))
                   (let [display (.getElementById js/document "initial-ch")]
                     ;(swap! app-state assoc :initial-challenge response)
                     (st/update-app-state [:initial-challenge] "initial-ch")))
        error-handler (fn [{:keys [status status-text]}]
                        (js/alert (str "ERR " status status-text)))]
    (http/call-get link callback error-handler)))


(defn main []
  (load-initial-challenge d/initial-challenge-link)
  [dom/mdl
   [:div.mdl-layout.mdl-js-layout.dark-primary-color
    ;; (if (.match (.-userAgent js/navigator) "Android")
    ;;   [:div.dark-primary-color
    ;;    {:style {:padding "0px 0px 0px 0px"
    ;;                                     ;:height "50%"
    ;;             :color "white"
    ;;                                     ;:border-bottom "1px solid #455A64"
    ;;             :height "100%"
    ;;             }}
    ;;    (h/header-component app-state)])
    (h/header-component)
    (h/drawer-component)
    [:main.mdl-layout__content
     {:on-click h/close-drawer}
     [:div.page-content.mdl-color--white
      [s/home]
      (soc/social-menu)
      [s/the-challenge]
      (s/registration
       d/registration-form-fields
       (st/read-app-state :initial-challenge)
       d/opened-registration?)
      (s/speakers d/speaker-list )
      (s/sponsors d/sponsor-list)
      (s/contributors
       d/contribution-roles
       d/contribution-form-fields)
      [f/footer-component]]]]])

(defn init []
  (dom/render-main main "app"))

(defn reload[]
  (dom/render-main main "app"))
