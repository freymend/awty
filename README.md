# Are We There Yet?

Remember when you were a kid on a long car ride, and you ket asking your parents "Are we there yet?" and annyoing the heck out of them? We want an application that does exactly that.

In this part, we will just pop `Toast` asking the question; in a future part, we will wire it up against SMS so that you can annoy the heck out of somebody else over text messages.

## Stories

As a user, when I start the application, it should present me with an activity containing an `EditText` for the message I want to send, an `EditText` for the phone number to which to send it (which we will not do anything with for this part), and `EditText` for how many minutes between each nag (no zeros, no negatives, must be an integer), and a BUtton labeled "Start".

As a user, when I am using the application's UI, `Start` should do nothing until all controls are filled out with legitimate values.

As a user, when I am using the application `Start` is pressed, the Button's text should switch to "Stop", and pushing `Stop` should cause the service to stop sending messages.

As a user, when I `Start` this service, it should begin to "send messages" every `N` minutes, as given by the user in the `EditText` UI. For now, these should be using the format: "(425) 555-1212:Are we there yet?".

As a user, when I swtich away from this application to another one, the messages should continue very `N` minutes, and continue to send until I navigate back to this application and push `Stop`.

## Grading

repo should be called `awty`

repo should contain all necessary build artifacts

We will clone and build it from the GH repo

5 points, one for each story

### Extra credit

1 pt: pushing `Start` pops dialog/`Toast` describing why the application cannot start (if it cannot start due to illegitimate values)

1 pt: create a custom `Toast` display for each "send" that has `Texting {phone#}` as a caption and the message itself as the body
