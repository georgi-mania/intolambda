(ns intolambda.socials
  (:require
   [cljsjs.material]
   [intolambda.http-utils :as http]
   [intolambda.data :as d]))

(defn- facebook-button [link]
  [:div.square
   [:a.mdl-button.mdl-js-button.mdl-button--facebook.mdl-button--raised.mdl-js-ripple-effect.mdl-button--icon.innactive.mdl-button--disabled
    {:href link
     :target "_blank"
     :title "Share event on Facebook"
     :disabled true}
    [:i.fa.fa-facebook.fa-fw]]])

(defn- twitter-button [link]
  [:div.square
   [:a.mdl-button.mdl-js-button.mdl-button--twitter.mdl-button--raised.mdl-js-ripple-effect.mdl-button--icon.innactive.mdl-button--disabled
    {:href link
     :title "Twit event"
     :target "_blank"}
    [:i.fa.fa-twitter.fa-fw]]])

(defn- linkedin-button [link data]
  [:div.square
   [:button.mdl-button.mdl-js-button.mdl-button--linkedin.mdl-button--raised.mdl-js-ripple-effect.mdl-button--icon.innactive.mdl-button--disabled
    {;:href "http://www.linkedin.com/shareArticle?mini=true&url=&title=&summary="
     :on-click #(http/call-post link data nil nil)
     :target "_blank"
     :title "Share event on LinkedIn"}
    [:i.fa.fa-linkedin.fa-fw]]])

(defn social-menu []
  [:nav.floating-menu
   [:div
    [facebook-button d/facebook-post-link]
    [twitter-button d/twitter-post-link]
    [linkedin-button d/linkedin-post-link d/linkedin-share-content ]]])
