(ns intolambda.footer
  (:require
   [cljsjs.material]
   [intolambda.utils :refer [icon-text-line icon-link show-element]]))

(defn footer-component [roles contributors]
  [:section
   {:id "footer"}
   [:div.content-grid.mdl-grid.dark-primary-color.text-primary-color

    [:div.mdl-cell.md-cell--4-col.md-cell--2-col-tablet.md-cell--2-col-phone
     [:h5.mdl-color-text--accent (icon-text-line "info_outline" "about")]
     (icon-text-line "event" "to be announced" "with-ear-muffs")
     (icon-text-line "place" "to be announced" "with-ear-muffs")
                                        ;(icon-link "assignment" "terms and conditions" "terms.html")
     ]

    [:div.mdl-cell.md-cell--4-col.md-cell--2-col-tablet.md-cell--2-col-phone
     [:h5.mdl-color-text--accent (icon-text-line "power" "contact")]
     (icon-text-line "home" "www.icslab.eu")
     (icon-text-line "mail" "icslabcrew@gmail.com")
                                        ;(icon-text-line "" "TODO: link to facebook event page")
     ]

    [:div.mdl-cell.md-cell--4-col.md-cell--2-col-tablet.md-cell--2-col-phone
     [:h5.mdl-color-text--accent (icon-text-line "person_add" "contribute")]
     [:p "Would you like to contribute as volunteer, expert, sponsor or partner?"
      [:button.mdl-color-text--accent.dark-primary-color.link-like
       {:on-click #(do
                     (show-element "contributors")
                     (set! (.-location js/document) "#contributors"))}
       "Let's get in touch"]]]]])
