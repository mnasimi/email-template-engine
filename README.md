**Service Name:
Email Template Service

**Description:**
This is a Spring Boot service developed to handle user information such as name and email, and utilize predefined email templates to generate personalized email content. This service takes user input, processes it, and seamlessly replaces placeholders within the templates with the provided user information. The service is ideal for sending personalized emails like welcome messages, notifications, and other automated communications.

**Key Features:**
- User Information Handling: Accepts user details such as name and email.
- Predefined Templates: Stores and manages a collection of email templates.
- Placeholder Replacement: Replaces placeholders in templates with user-specific information.
- Email Generation: Produces the final email content ready for sending.

**Example:**

```
Dear ${name},

Welcome to our service! We're excited to have you on board.
If you have any questions, feel free to contact us at ${email}.

Best regards,
${company}
```

```
Dear John Doe,

Welcome to our service! We're excited to have you on board.
If you have any questions, feel free to contact us at support@example.com.

Best regards,
Tech Company
```
