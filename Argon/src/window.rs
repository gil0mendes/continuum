use factory::Factory;
use renderer::Renderer;
use glutin;
use glutin::GlContext;

pub struct WindowBuilder {
    /// Window dimensions
    dimensions: (u32, u32),
    /// Create the window in fullscreen
    fullscreen: bool,
    /// Window title
    title: String,
}

impl WindowBuilder {
    /// Create a new window builder
    pub fn new(title: &str) -> Self {
        Self {
            dimensions: (1024, 768),
            fullscreen: false,
            title: title.to_owned()
        }
    }

    /// Define the window dimensions
    pub fn set_dimensions(&mut self, width: u32, height: u32) -> &mut Self {
        self.dimensions = (width, height);
        self
    }

    /// Set the fullscreen option
    pub fn set_fullscreen(&mut self, value: bool) -> &mut Self {
        self.fullscreen = value;
        self
    }

    /// Build a new window
    pub fn build(&mut self) -> Window {
        use glutin::get_primary_monitor;

        // create a new window builder
        let builder = if self.fullscreen {
            glutin::WindowBuilder::new().with_fullscreen(get_primary_monitor())
        } else {
            glutin::WindowBuilder::new()
        };

        // set dimensions and title
        let builder = builder.clone()
            .with_dimensions(self.dimensions.0, self.dimensions.1)
            .with_title(self.title.clone());

        // create a context
        let context = glutin::ContextBuilder::new();

        // create a event loop
        let event_loop = glutin::EventsLoop::new();

        // create a renderer, window object and the factory object
        let (renderer, window, mut factory) = Renderer::new(builder, context, &event_loop);

        // build a window object
        Window {
            event_loop,
            window,
            renderer,
            factory
        }
    }
}

pub struct Window {
    /// Internal event loop
    event_loop: glutin::EventsLoop,
    /// Glutin window
    window: glutin::GlWindow,
    /// Render instance
    renderer: Renderer,
    /// Argon factory
    factory: Factory,
}

impl Window {
    pub fn update(&mut self) -> bool {
        let mut running = true;
        let renderer = &mut self.renderer;

        // swap buffers
        self.window.swap_buffers().unwrap();
        let window = &self.window;

        // poll events from the event loop
        self.event_loop.poll_events(|event| {
            use glutin::WindowEvent::Closed;

            // TODO implement
            match event {
                glutin::Event::WindowEvent { event, .. } => match event {
                    // TODO handle resize
                    Closed => running = false,
                    // ignore other events
                    _ => {}
                },
                // ignore other events
                _ => {}
            }
        });

        running
    }
}
