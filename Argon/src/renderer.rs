use gfx;
use gfx::traits::{Device, Factory as Factory_, FactoryExt};
#[cfg(feature = "opengl")]
use gfx_device_gl as back;
#[cfg(feature = "opengl")]
use gfx_window_glutin;
#[cfg(feature = "opengl")]
use glutin;

use factory::Factory;

pub use self::back::Factory as BackendFactory;

pub type ColorFormat = gfx::format::Srgba8;
pub type DepthFormat = gfx::format::DepthStencil;

gfx_defines! {
    vertex Vertex {
        pos: [f32; 4] = "a_Position",
        color: [f32; 3] = "a_Color",
    }

    pipeline pipe {
        vbuf: gfx::VertexBuffer<Vertex> = (),
        out: gfx::RenderTarget<gfx::format::Srgba8> = "Target0",
    }
}

pub struct Renderer {
    device: back::Device,
}

impl Renderer {
    pub fn new(builder: glutin::WindowBuilder,
               context: glutin::ContextBuilder,
               event_loop: &glutin::EventsLoop) -> (Self, glutin::GlWindow, Factory) {
        // create a gfx glutin window
        let (window, device, mut gl_factory, color, depth) = gfx_window_glutin::init::<ColorFormat, DepthFormat>(builder, context, event_loop);

        // create a new renderer instance
        let renderer = Renderer {
            device
        };

        // create a new argon factory
        let factory = Factory::new(gl_factory);

        // return
        (renderer, window, factory)
    }
}
