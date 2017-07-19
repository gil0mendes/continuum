extern crate argon;

fn main() {
    // build a new window
    let mut window = argon::WindowBuilder::new("Continuum").build();

    // main loop
    while window.update() {}
}
