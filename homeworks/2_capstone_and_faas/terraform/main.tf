terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "3.5.0"
    }
  }
}

terraform {
  required_version = "1.2.3"
  backend "gcs" {
    bucket = "okyshkevych-tf-learning"
    prefix = "faas/state"
  }
}
provider "google" {
  project = var.project
  region  = var.region
  zone    = var.zone
}

resource "google_cloudfunctions_function" "function" {
  name        = "faas-learning"
  runtime     = "java11"
  description = "Cloud function for UCU course. Simple HTTP function that stores request to bucket"

  available_memory_mb = 256
  trigger_http        = true
  source_repository {
    url = "https://source.developers.google.com/projects/${var.project}/repos/${var.repository}/moveable-aliases/${var.branch}/paths/${var.source_path}"
  }
  entry_point = "functions.FaasLearning"

}

resource "google_cloudfunctions_function_iam_member" "invoker" {
  project        = google_cloudfunctions_function.function.project
  region         = google_cloudfunctions_function.function.region
  cloud_function = google_cloudfunctions_function.function.name

  role   = "roles/cloudfunctions.invoker"
  member = "user:oleh.kyshkevych29@gmail.com"
}