(ns ctim.schemas.actor
  (:require [ctim.schemas.common :as c]
            [ctim.schemas.relationships :as rel]
            [ctim.schemas.vocabularies :as v]
            [schema.core :as s]
            [schema-tools.core :as st]))

(s/defschema Actor
  "http://stixproject.github.io/data-model/1.2/ta/ThreatActorType/"
  (st/merge
   c/GenericStixIdentifiers
   {:valid_time c/ValidTime
    :actor_type v/ThreatActorType
    :tlp c/TLP}
   (st/optional-keys
    {:source s/Str
     :identity c/Identity
     :motivation v/Motivation
     :sophistication v/Sophistication
     :intended_effect v/IntendedEffect
     :planning_and_operational_support s/Str ; Empty vocab
     :observed_TTPs rel/RelatedTTPs
     :associated_campaigns rel/RelatedCampaigns
     :associated_actors rel/RelatedActors
     :confidence v/HighMedLow
     ;; Not provided: handling
     ;; Not provided: related_packages (deprecated)
     })))

(s/defschema Type
  (s/enum "actor"))

(s/defschema NewActor
  "Schema for submitting new Actors"
  (st/merge
   Actor
   (st/optional-keys
    {:id c/ID
     :valid_time c/ValidTime
     :type Type
     :tlp c/TLP})))

(s/defschema StoredActor
  "An actor as stored in the data store"
  (c/stored-schema "actor" Actor))
