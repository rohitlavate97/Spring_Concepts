## We are using some couple of microservices developed in `spring boot` with AWS Components
- **RDS**
- **DynamoDB**
- **Lambda** 
- **CloudWatch Trigger with Event Bridge**
- **S3 Bucket**

## Cloud Service Models

### Infrastructure as a Service (IAAS)

- **Description:**
    - IAAS provides virtualized computing resources over the internet.
    - Users can rent virtual machines, storage, and networking infrastructure.
    - It offers flexibility and scalability, allowing users to scale resources as needed.

- **AWS Services:**
    - Amazon EC2 (Elastic Compute Cloud)
    - Amazon S3 (Simple Storage Service)

- **Explanation:**
    - **Amazon EC2 (Elastic Compute Cloud):** IAAS offering that provides resizable compute capacity in the cloud. Users have control over the virtual machines and can configure them according to their requirements.

    - **Amazon S3 (Simple Storage Service):** IAAS offering for scalable object storage. It allows users to store and retrieve any amount of data, making it suitable for various use cases, including backup and archive.

---

### Platform as a Service (PAAS)

- **Description:**
    - PAAS delivers a platform allowing customers to develop, run, and manage applications without dealing with the complexity of underlying infrastructure.
    - It streamlines the development process, making it more efficient.

- **AWS Services:**
    - AWS Elastic Beanstalk
    - AWS Lambda (partially PAAS)

- **Explanation:**
    - **AWS Elastic Beanstalk:** PAAS offering that automates the deployment and scaling of applications. Developers can focus on writing code while AWS manages the underlying infrastructure.

    - **AWS Lambda:** While often considered Serverless (FaaS), AWS Lambda can be viewed as a partial PAAS solution. It allows developers to run code without provisioning or managing servers.

---

### Software as a Service (SAAS)

- **Description:**
    - SAAS delivers software applications over the internet, eliminating the need for users to install, maintain, and upgrade the software.

- **AWS Services:**
    - Amazon RDS (Relational Database Service)
    - Amazon DynamoDB
    - Amazon SQS (Simple Queue Service)
    - Amazon SNS (Simple Notification Service)
    - CloudWatch
    - AWS Lambda (partially SAAS)

- **Explanation:**
    - **Amazon RDS:** SAAS offering that provides managed relational databases. Users don't need to worry about database administration tasks.

    - **Amazon DynamoDB:** SAAS NoSQL database service that automatically scales based on demand. Users can focus on application development without managing the database infrastructure.

    - **Amazon SQS:** SAAS offering for message queue services, eliminating the need to manage message-oriented middleware.

    - **Amazon SNS:** SAAS offering for push notification services, simplifying the delivery of messages or notifications.

    - **CloudWatch:** SAAS for monitoring and managing AWS resources. Users can gain insights into application performance without managing monitoring infrastructure.

    - **AWS Lambda:** As a Serverless (FaaS) service, AWS Lambda can be considered a partial SAAS solution, allowing users to run code without dealing with server management.