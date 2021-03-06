(ns ctim.generators.schemas.sighting-generators
  (:require [clojure.test.check.generators :as gen]
            [ctim.lib.time :as time]
            [ctim.schemas
             [common :as schemas-common]
             [sighting :refer [NewSighting StoredSighting]]]
            [ctim.generators.common
             :refer [leaf-generators maybe]
             :as common]
            [ctim.generators.id :as gen-id]))

(def gen-short-id
  (gen-id/gen-short-id-of-type :sighting))

(defn complete [m]
  (common/complete
   StoredSighting
   m))

(defn complete-new [m]
  (common/complete
   NewSighting
   m))

(def gen-sighting
  (gen/fmap
   (fn [id]
     (complete
      {:id id}))
   gen-short-id))

(defn gen-sighting-with-observables [observables]
  (gen/fmap
   (fn [id]
     (complete
      {:id id
       :observables observables}))
   gen-short-id))

(defn gen-new-sighting-with-indicator [indicator-long-id]
  (gen/fmap
   (fn [id]
     (complete-new
      {:id id
       :indicators [{:indicator_id indicator-long-id}]}))
   gen-short-id))

(def gen-new-sighting
  (gen/fmap
   (fn [id]
     (common/complete
      NewSighting
      (cond-> {}
        id (assoc :id id))))
   (maybe gen-short-id)))
