(ns intolambda.sections
  (:require
   [cljsjs.material]
   [intolambda.registration :refer [register]]
   [intolambda.contribution :refer [contribution-component clear-selections]]
   [intolambda.utils :refer [build-speaker-box
                             build-sponsor-box
                             display-grid
                             show-element]]))

(defn home []
  [:section
   {:id "home"}
   [:div.page-content.intolambda-center-align {:id "bg"}]])

(defn the-challenge []
  [:section
   {:id "the-challenge"}
   [:div.page-content
    [:h3.dark-primary-color.text-primary-color "the challenge"]
    [:div.mdl-color-text--primary
     {:style {:padding "10px"}}
     [:p
      [:span.thick "into-lambda"]
      [:span " is a new challenging event in the functional programming environment in Romania."]]
     [:p
      [:span "The journey starts with some enlightening talks given by "]
      [:a.mdl-color-text--teal
       {:href "#speakers"}
       "our experts"]
      [:span " which trigger your intuition and curiosity for the hackathon that will follow."]]
     [:p "Grouped in ad-hoc organized teams, you will then join the venture of designing and implementing a new API while improving your Clojure skills and having fun."]
     [:p.thick "More details will be available soon!" ]]]])

(defn registration [registration-form-fields initial-challenge state]
  [:section
   {:id "register"}
   (register registration-form-fields initial-challenge state)])

(defn speakers [speaker-list]
  [:section
   {:id "speakers"}
   (display-grid "speakers" speaker-list build-speaker-box)])

(defn sponsors [sponsor-list]
  [:section
   {:id "sponsors"}
   [:div
    (display-grid "sponsors" sponsor-list build-sponsor-box)]])

(defn contributors [roles contributors]
  [:section
   {:id "contributors"
    :hidden true}
   (contribution-component
    roles
    contributors
    #(do
       (show-element "contributors" true)
       (clear-selections roles)))])
