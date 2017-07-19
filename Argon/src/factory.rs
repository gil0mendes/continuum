use renderer::BackendFactory;

pub struct Factory {
    backend: BackendFactory
}

impl Factory {
    pub fn new(mut backend: BackendFactory) -> Self {
        Factory {
            backend
        }
    }
}
