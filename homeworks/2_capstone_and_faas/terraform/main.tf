terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "3.5.0"
    }
  }
}

terraform {
  required_version = "1.1.9"
  backend "gcs" {
    bucket  = "okyshkevych-tf-learning"
    prefix  = "faas/state"
  }
}
