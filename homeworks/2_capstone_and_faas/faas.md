# Cloud Function

To deploy **Cloud Function** you need to specify path to source code. So I need to use **Cloud Source Repositories**. It can connect to github repository like this
![cloud source repository sync with github](../../files/homeworks/2_capstone_and_faas/cloud_source_repository_sync_with_github.png)

After writing my function, I've decided to deploy it by using [terraform](./terraform/main.tf).

I've added authentication, so only my user can trigger this function. 
Here is example what will happen if you try to trigger function being unauthorized.

![unauthorized try to access](../../files/homeworks/2_capstone_and_faas/unauthorized_try_to_access.png)   

To trigger Cloud Function you need to provide [access token](https://cloud.google.com/functions/docs/securing/authenticating#authenticating_developer_testing)

By authenticating to gcloud cli I can run 
```bash
curl -m 70 -X POST https://us-central1-terraform-learning-349712.cloudfunctions.net/faas-learning \
-H "Authorization:bearer $(gcloud auth print-identity-token)" \
-H "Content-Type:application/json"
```
![request](../../files/homeworks/2_capstone_and_faas/cloud_function_request.png)

You can see logs of execution

![logs](../../files/homeworks/2_capstone_and_faas/cloud_function_logs.png)

And result is stored to the GCP Object Storage

![file result](../../files/homeworks/2_capstone_and_faas/cloud_function_result.png)

Content of this request

![file content](../../files/homeworks/2_capstone_and_faas/file_content.png)