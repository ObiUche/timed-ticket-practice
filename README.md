## Current Status

### Completed

- Service layer tests have been added for create, read, update, and delete behaviour.
- Success and failure paths are now covered for key service methods.
- Delete behaviour is tested by verifying the repository delete call.
- Missing task scenarios throw `EntityNotFoundException`.

### Known Issues / Next Improvements

- Controller path variables still need fixing.
- REST design should be improved to use `id` for read, update, and delete operations instead of title.
- Controller tests still need to be completed.
- API response handling should be cleaned up with better status codes and error messages.

### Current Review Grade

**Average grade: 7.29/10**

The service tests have improved significantly. The biggest remaining improvement areas are the controller layer, REST endpoint design, and controller tests.