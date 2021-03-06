(ns ctim.generators.schemas.actor-generators
  (:require [clojure.test.check.generators :as gen]
            [ctim.lib.time :as time]
            [ctim.schemas
             [actor :refer [NewActor StoredActor]]
             [common :as schemas-common]]
            [ctim.generators.common
             :refer [complete leaf-generators maybe]
             :as common]
            [ctim.generators.id :as gen-id]))

(def gen-actor
  (gen/fmap
   (fn [id]
     (complete
      StoredActor
      {:id id}))
   (gen-id/gen-short-id-of-type :actor)))

(def gen-new-actor
  (gen/fmap
   (fn [[id
         [start-time end-time]]]
     (complete
      NewActor
      (cond-> {}
        id
        (assoc :id id)

        start-time
        (assoc-in [:valid_time :start_time] start-time)

        end-time
        (assoc-in [:valid_time :end_time] end-time))))
   (gen/tuple
    (maybe (gen-id/gen-short-id-of-type :actor))
    ;; complete doesn't seem to generate :valid_time values, so do it manually
    common/gen-valid-time-tuple)))
