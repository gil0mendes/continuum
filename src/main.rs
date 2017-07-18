extern crate glutin_window;
#[macro_use]
extern crate gfx;
extern crate gfx_core;
extern crate gfx_device_gl;
extern crate gfx_voxel;
extern crate piston;
extern crate vecmath;

pub use gfx_voxel::{array, cube};

use array::*;
use gfx_voxel::texture::{AtlasBuilder, ImageSize, Texture};
use glutin_window::*;
use piston::window::{Size, Window, OpenGLWindow, WindowSettings};
use piston::event_loop::{Events, EventSettings, EventLoop};
use shader::Renderer;
use std::path::{Path, PathBuf};

mod shader;

fn create_main_targets(dim: gfx::texture::Dimensions) ->
(gfx::handle::RenderTargetView<
    gfx_device_gl::Resources, gfx::format::Srgba8>,
 gfx::handle::DepthStencilView<
     gfx_device_gl::Resources, gfx::format::DepthStencil>) {
    use gfx_core::memory::Typed;
    use gfx::format::{DepthStencil, Format, Formatted, Srgba8};

    let color_format: Format = <Srgba8 as Formatted>::get_format();
    let depth_format: Format = <DepthStencil as Formatted>::get_format();
    let (output_color, output_stencil) =
        gfx_device_gl::create_main_targets_raw(dim,
                                               color_format.0,
                                               depth_format.0);
    let output_color = Typed::new(output_color);
    let output_stencil = Typed::new(output_stencil);
    (output_color, output_stencil)
}

fn main() {
    let assets = Path::new("../assets");
    let texture_file_path = assets.join(Path::new("textures"));

    // create a new window
    let mut window: GlutinWindow = WindowSettings::new(
        "Continuum",
        Size { width: 854, height: 480 })
        .build()
        .unwrap();

    // creates a GFX device
    let (mut device, mut factory) = gfx_device_gl::create(|s| window.get_proc_address(s) as *const _);

    // get window size
    let Size { width: w, height: h } = window.size();

    let (target_view, depth_view) = create_main_targets(
        (w as u16, h as u16, 1, (0 as gfx::texture::NumSamples).into()));

    // creates a new encoder
    let encoder = factory.create_command_buffer().into();

    // create a new atlas builder
    let mut atlas = AtlasBuilder::new(texture_file_path, 16, 16);
    let texture = atlas.complete(&mut factory);

    // create a render
    let mut renderer = Renderer::new(factory, encoder, target_view, depth_view, texture.surface.clone());

    // create the event loop
    let mut events = Events::new(EventSettings::new().ups(120).max_fps(200));

    while let Some(event) = events.next(&mut window) {
        use piston::input::Input;

        match event {
            Input::Render(_) => {
                // clean the previous frame
                renderer.clear();

                // TODO: render things

                // flush the device
                renderer.flush(&mut device);
            }

            // ignore everything else
            _ => {}
        };
    };
}
