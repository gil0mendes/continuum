use gfx::traits::FactoryExt;
use gfx;
use vecmath::{self, Matrix4};

gfx_defines! {
    vertex Vertex {
        pos: [f32; 2] = "a_Pos",
        color: [f32; 3] = "a_Color",
    }

    pipeline pipe {
        vbuf: gfx::VertexBuffer<Vertex> = (),
        out: gfx::RenderTarget<gfx::format::Srgba8> = "Target0",
    }
}

pub struct Renderer<R: gfx::Resources, F: gfx::Factory<R>, C: gfx::CommandBuffer<R>> {
    factory: F,
    pub pipeline: gfx::PipelineState<R, pipe::Meta>,
    data: pipe::Data<R>,
    encoder: gfx::Encoder<R, C>,
    clear_color: [f32; 4],
    clear_depth: f32,
    clear_stencil: u8,
    slice: gfx::Slice<R>,
}

impl<R: gfx::Resources, F: gfx::Factory<R>, C: gfx::CommandBuffer<R>> Renderer<R, F, C> {
    pub fn new(mut factory: F, encoder: gfx::Encoder<R, C>, target: gfx::handle::RenderTargetView<R, gfx::format::Srgba8>,
               depth: gfx::handle::DepthStencilView<R, (gfx::format::D24_S8, gfx::format::Unorm)>,
               tex: gfx::handle::Texture<R, gfx::format::R8_G8_B8_A8>) -> Self {
        // create a new sampler
        let sampler = factory.create_sampler(
            gfx::texture::SamplerInfo::new(
                gfx::texture::FilterMethod::Scale,
                gfx::texture::WrapMode::Tile
            )
        );

        // create the texture view
        let texture_view = factory.view_texture_as_shader_resource::<gfx::format::Rgba8>(
            &tex, (0, 0), gfx::format::Swizzle::new()
        ).unwrap();

        // link the two shader
        let prog = factory.link_program(
            include_bytes!("../assets/shader/initVertex.glsl"),
            include_bytes!("../assets/shader/initFrag.glsl")
        ).unwrap();

        let mut rasterizer = gfx::state::Rasterizer::new_fill();
        rasterizer.front_face = gfx::state::FrontFace::Clockwise;
        let pipe = factory.create_pipeline_from_program(&prog, gfx::Primitive::TriangleList, rasterizer, pipe::new()).unwrap();

        // create vertex buffer
        let vbuf = factory.create_vertex_buffer(&[]);
        let slice = gfx::Slice::new_match_vertex_buffer(&vbuf);

        let data = pipe::Data {
            vbuf: vbuf,
            out: target
        };

        Renderer {
            factory,
            pipeline: pipe,
            data,
            encoder,
            clear_color: [0.81, 0.8, 1.0, 1.0],
            clear_depth: 1.0,
            clear_stencil: 0,
            slice,
        }
    }

    /// Clear the previous frame.
    pub fn clear(&mut self) {
        self.encoder.clear(&self.data.out, self.clear_color);
        // self.encoder.clear_depth(&self.data.out_depth, self.clear_depth);
        // self.encoder.clear_stencil(&self.data.out_depth, self.clear_stencil);
    }

    /// Flush the buffer
    ///
    /// This makes send a new frame to the screen.
    pub fn flush<D: gfx::Device<Resources=R, CommandBuffer=C> + Sized>(&mut self, device: &mut D) {
        self.encoder.flush(device);
    }

    pub fn create_buffer(&mut self, data: &[Vertex]) -> gfx::handle::Buffer<R, Vertex> {
        let vbuf = self.factory.create_vertex_buffer(data);
        self.slice = gfx::Slice::new_match_vertex_buffer(&vbuf);

        vbuf
    }

    /// Create a new buffer with
    pub fn create_buffer_with_indices(&mut self, data: &[Vertex], indices: &[u16]) -> (gfx::handle::Buffer<R, Vertex>, gfx::Slice<R>) {
        self.factory.create_vertex_buffer_with_slice(data, indices)
    }

    /// Render the buffer
    ///
    /// Render a new frame
    pub fn render(&mut self, buffer: &mut gfx::handle::Buffer<R, Vertex>) {
        self.data.vbuf = buffer.clone();
        self.slice.end = buffer.len() as u32;
        self.encoder.draw(&self.slice, &self.pipeline, &self.data);
    }

    pub fn render_with_slice(&mut self, buffer: &mut gfx::handle::Buffer<R, Vertex>, slice: &gfx::Slice<R>) {
        // update the local slice
        self.slice = slice.clone();

        // render the vertex set
        self.render(buffer);
    }
}
