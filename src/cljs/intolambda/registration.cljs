(ns intolambda.registration
  (:require
   [cljsjs.material]
   [intolambda.form-utils :refer [build-form-with-checkbox-validation]]
   [intolambda.utils :refer [show-element]]
   [intolambda.ui-components :as ui]))

(def form-id "rfd")
(def challenge-id "ch-id")
(def reg-id "reg-id")
(def help-id "help-id")

(defn- submit-fn []
  (js/alert "submit button pressed"))

(defn- initial-challenge-component [initial-challenge]
  [:div
   {:id challenge-id
    :hidden true}
   [:p.thick.mdl-color-text--primary "Solve the initial challenge to unlock the registration"]
   [:p.mdl-color-text--primary initial-challenge]])

(defn- build-registration-form [form-id registration-form-fields]
  [:div
   {:id form-id
    :hidden true}
   [:p.thick.mdl-color-text--primary ""]
   (build-form-with-checkbox-validation
    ""
    nil
    registration-form-fields
    submit-fn
    "I agree with the terms and conditions.")])

(defn- registration-not-open []
  [:div
   [:h5.mdl-color-text--primary "registration has not opened yet"]])

(defn- show-registration-form-and-challenge [form-id ch-id]
  (show-element form-id)
  (show-element ch-id))

(defn- show-registration [form-id ch-id state]
  (if state
    (show-registration-form-and-challenge form-id ch-id)
    (ui/show-modal reg-id)))

(defn- show-help []
  [:div.mdl-color-text--primary
   [:div
    [:span.thick "defn"]
    [:span " is a macro that allows us to easily define a function. The syntax is:"]
    [:p.with-whitespace "(defn fn-name arg-vector fn-comment fn-body)"]]
   [:div
    [:span.thick "->>"]
    [:span " is a threading macro that inserts x as the last item in the first form and then inserts the first form as last argument in the next form. The syntax is:"]
    [:p.with-whitespace "(->> x & forms)"]]
   [:p
    [:span.thick "(f)"]
    [:span " invokes the function f."]]
   [:p
    [:span "A peek at Clojure documentation can be found "]
    [:span
     [:a.mdl-color-text--teal
      {:href "http://clojuredocs.org/quickref"
       :target "_blank"}
      "here."]]]
   [:p
    [:span "An online Clojure REPL can be found "]
    [:span
     [:a.mdl-color-text--teal
      {:href "http://www.tryclj.com/"
       :target "_blank"}
      "here."]]]])

(defn- call-to-register [state]
  [:div
   [:div.code.mdl-color-text--primary
    [:p
     [:span.thick "(defn "]
     [:span.thick.mdl-color-text--accent " register "]
     [:span "[participant]"]]
    [:p.secondary-text-color.with-whitespace  "\"register participant to the hackathon\""]
    [:p.with-whitespace
     [:span "("]
     [:span.thick "->>"]]
    [:p.with-whitespacex2 "(solve-the-initial-challenge)"]
    [:p.with-whitespacex2 "(input-contact-details participant)"]
    [:p.with-whitespacex2
     [:span "(pay-registration-fee)))"]]
    [:p
     [:span.thick "user>"]
     [:span "(into-lambda/register me)"]
     [:span
      [:button.mdl-button.mdl-js-button.mdl-button--icon.accent-color
       {:on-click (fn [] (show-registration form-id challenge-id state))}
       [:i.material-icons "touch_app"]]]]]
   [:button.link-like.mdl-color--white.mdl-color-text--teal
    {:on-click #(ui/show-modal help-id)}
    "I'm new to Clojure. Help me read this!"]])

(defn register [registration-form-fields initial-challenge state]
  [:div.page-content
   [:h3.dark-primary-color.text-primary-color "register"]

   (ui/build-modal
    reg-id
    "registration"
    (registration-not-open)
    [{:action-name "OK" :action-fn (partial ui/close-modal reg-id)}])

   (ui/build-modal
    help-id
    "help"
    (show-help)
    [{:action-name "Got it!" :action-fn (partial ui/close-modal help-id)}])

   [:div.content-grid.mdl-grid.mdl-js-grid
    [:div.mdl-cell.mdl-cell--4-col
     (call-to-register state)]
    [:div.mdl-cell.mdl-cell--4-col
     [initial-challenge-component initial-challenge]]
    [:div.mdl-cell.mdl-cell--4-col
     [build-registration-form form-id registration-form-fields]]]])
