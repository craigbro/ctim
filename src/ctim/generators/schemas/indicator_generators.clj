(ns ctim.generators.schemas.indicator-generators
  (:require [clojure.test.check.generators :as gen]
            [ctim.lib.time :as time]
            [ctim.schemas
             [common :as schemas-common]
             [indicator :refer [NewIndicator StoredIndicator]]]
            [ctim.generators.common
             :refer [complete leaf-generators maybe]
             :as common]
            [ctim.generators.id :as gen-id]))

(def gen-short-id
  (gen-id/gen-short-id-of-type :indicator))

(def gen-indicator
  (gen/fmap
   (fn [id]
     (complete
      StoredIndicator
      {:id id}))
   gen-short-id))

(defn gen-new-indicator_ [gen-id]
  (gen/fmap
   (fn [[id
         [start-time end-time]]]
     (complete
      NewIndicator
      (cond-> {}
        id
        (assoc :id id)

        start-time
        (assoc-in [:valid_time :start_time] start-time)

        end-time
        (assoc-in [:valid_time :end_time] end-time))))
   (gen/tuple
    gen-id
    ;; complete doesn't seem to generate :valid_time values, so do it manually
    common/gen-valid-time-tuple)))

(def gen-new-indicator
  (gen-new-indicator_
   (maybe gen-short-id)))

(def gen-new-indicator-with-id
  (gen-new-indicator_
   gen-short-id))
