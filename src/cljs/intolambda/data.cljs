(ns intolambda.data)

(def opened-registration? false)

;;TODO: define ids and remove hardcoded values
;;(def sections {:home {:id "home"}})

(def speaker-list
  "List of all speakers and their details. :avatar is the name of the speaker's picture saved in /images/speakers/ folder. E.g. joedoe for /images/speakers/joedoe.png."
  [{:niste-date-dummy :in-interior} ;; spacer
   {:name "Alexandru Gherega"
    :desc "Clojure enthusiast"
    :avatar "0a53-alex-gherega_424242.jpg"
    :link "http://www.icslab.eu"}
   {:name "Speaker"
    :desc "Join as speaker"
    :avatar "joedoe-f.png"
    :link "#contributors"};spacer
   ])

(def sponsor-list
  "List of all sponsors and their details. :avatar is the name of the sponsor's brand image / logo saved in /images/sponsors/ folder. "
  [{:ndd :in-interior}
   {:name "s1" :id "icslab" :avatar "logo-1-fb.png"
    :link "http://www.icslab.eu"
    :tooltip "icslab.eu"}
   {:name "s2" :id "new-sponsor" :avatar "joedoe-d.png"
    :link "#contributors"
    :tooltip "Become a sponsor!"}])

;; "[A-Za-z0-9]+@{1,}[A-Za-z0-9]+"

(def registration-form-fields
  [{:field-name "name"
    :field-type "text"
    :pattern "[A-Za-z\\s]+"
    :err-msg "invalid name"}

   {:field-name "email"
    :field-type "text"
    :pattern "[A-Za-z0-9_@\\.]+"
    :err-msg "invalid email address"}

   {:field-name "phone"
    :field-type "text"
    :pattern "[0-9]{10,}"
    :err-msg "e.g. 07XXXXXXXX"}

   {:field-name "solution"
    :field-type "text"
    :validator "[A-Za-z0-9]+"
    :err-msg "please input solution"}])
;; email? "[A-Za-z0-9_]+@{1,}([A-Za-z0-9] +\\. {1,} [A-Za-z]+)+"

(def contribution-roles
  ["volunteer" "speaker" "sponsor" "partner"])

(def contribution-form-fields
  [{:field-name "name"
    :field-type "text"
    :pattern "[A-Za-z\\s]+"
    :err-msg "invalid name"}

   {:field-name "email"
    :field-type "text"
    :pattern "[A-Za-z0-9_@\\.]+"
    :err-msg "invalid email address"}

   {:field-name "phone"
    :field-type "text"
    :pattern "[0-9]{10,}"
    :err-msg "e.g. 07XXXXXXXX"}

   {:field-name "contribution"
    :field-type "text"
    :validator "[A-Za-z0-9]+"
    :err-msg "what's your contribution?"
    :field-display "textarea"}])

(def initial-challenge-link "http://www.icslab.eu/legal/codo-legal-terms.html")

;; LinkedIn share information
(def linkedin-share-content
  {:comment ""
   :content {:title "into lambda hackathon"
             :description "Fun Clojure hackathon"
             :submitted-url "TODO:add url"
             :submitted-image-url "TODO:add image"
             }
   :visibility {:code "anyone"}})

(def linkedin-post-link
  "https://api.linkedin.com/v1/people/~/shares?format=json")

(def facebook-post-link
  "https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fwww.icslab.eu%2F&amp;src=sdkpreparse")

(def twitter-post-link
   "http://twitter.com/home?status=")
