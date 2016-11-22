(ns intolambda.form-utils
  (:require
   [cljsjs.material]
   [intolambda.utils :refer [load-element build-checkbox]]))

(enable-console-print!)

(defn- toggle-submit-button [check-id button-id]
  (let [check (load-element check-id)
        button (load-element button-id)]
    (set! (.-disabled button) (not (.-checked check)))))

(defn build-textarea [{:keys [field-name field-type pattern err-msg]}]
  [:div.mdl-textfield.mdl-js-textfield.mdl-texfield--floating-label.divider-color
   {:key (hash field-name)}
   [:textarea.mdl-textfield__input.mdl-color--white
    {:type field-type
     :id field-name
     :pattern pattern
     :name field-name
     :rows "5"}
    ]
   [:label.mdl-textfield__label {:for field-name} field-name]
   [:span.mdl-textfield__error err-msg]])

(defn build-textfield [{:keys [field-name field-type pattern err-msg]}]
  [:div.mdl-textfield.mdl-js-textfield.mdl-textfield--floating-label.divider-color
   {:key (hash field-name)}
   [:input.mdl-textfield__input.mdl-color--white
    {:type field-type
     :id field-name
     :pattern pattern
     :name field-name}]
   [:label.mdl-textfield__label {:for field-name} field-name]
   [:span.mdl-textfield__error err-msg]])

(defn build-field [data]
  (if (and
       (:field-display data)
       (= (:field-display data) "textarea"))
    (build-textarea data)
    (build-textfield data)))

(defn- build-easy-textfield [name type value]
  [:input.mdl-textfield__input
    {:type type
     :id name
     :name name
     :value value}])

(defn- build-submit-button [text id submit-fn disabled?]
  [:button.mdl-button.mdl-js-button.accent-color.primary-text-color
   {:id id
    :type "submit"
 ;   :on-click #(submit-fn)
    :disabled disabled?}
   text])

(defn- email-reply-to [email-addr]
  (build-easy-textfield "_replyTo" "hidden" email-addr))

(defn- email-subject [text]
  (build-easy-textfield "_subject" "hidden" text))

(defn- email-next [next-page]
  (build-easy-textfield "_next" "hidden" next-page))

(defn- extra-info []
  (build-easy-textfield "extra-info" "hidden" ""))

(defn build-form-with-checkbox-validation [form-name form-params input-data submit-fn checkbox-label]
  (let [submit-button-id "submit-button"
        form-params (or form-params {:action "#" :id "form" :method "POST"})
        checkbox (if checkbox-label (build-checkbox "submit-checkbox"
                                                    checkbox-label
                                                    toggle-submit-button
                                                    submit-button-id))]
    [:div
     [:h4.mdl-color-text--primary form-name]
     [:div.content-grid.mdl-grid.mdl-js-grid.intolambda-center-align
      [:div.mdl-cell.mdl-cell--middle.mdl-cell--12-col
       [:form
        form-params
        (map #(build-textfield %) input-data)
        checkbox
        (build-submit-button "submit" submit-button-id submit-fn true)]]]]))

(defn build-form-for-email [form-params input-data submit-fn close-fn]
  [:div.page-content.center-component
   [:div.content-grid.mdl-grid.mdl-js-grid
    [:div.mdl-cell.mdl-cell--4-col..mdl-cell--4-col-tablet.mdl-cell--2-col-phone
     [:form
      form-params
      (map #(build-field %) input-data)
      (email-subject "New contributor!!")
      (email-next "index.html")
      (email-reply-to "icslabcrew@gmail.com")
      (extra-info)
      (build-submit-button "submit" "submit-id" submit-fn false)]]
    [:div.mdl-cell.mdl-cell--6-col.mdl-cell--3-col-tablet.mdl-cell--1-col-phone]
    [:div.mdl-cell.mdl-cell--2-col.mdl-cell--1-col-tablet.mdl-cell--1-col-phone.mdl-cell--bottom [:a.mdl-color-text--teal.link-like {:on-click close-fn} "hide"]]]])
