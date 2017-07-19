#[macro_use]
extern crate gfx;
#[cfg(feature = "opengl")]
extern crate gfx_device_gl;
#[cfg(feature = "opengl")]
extern crate gfx_window_glutin;
#[cfg(feature = "opengl")]
extern crate glutin;

mod factory;
mod window;
mod renderer;

pub use window::WindowBuilder;
