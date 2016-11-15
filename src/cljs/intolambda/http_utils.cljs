(ns intolambda.http-utils
  (:require [ajax.core :refer [GET POST]]))

(defn- cljs->json [data]
  (.stringify js/JSON (clj->js data)))

(defn call-get [url handler-fn error-handler-fn]
  (GET url
       {:handler handler-fn
        :error-handler error-handler-fn
        :with-credentials false}))

(defn call-post [url message-map handler-fn error-handler-fn]
  (POST url
        {:params {:message (cljs->json message-map)}
         :handler handler-fn
         :error-handler error-handler-fn
         :format :json}))
