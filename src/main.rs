extern crate find_folder;
extern crate piston_window;

use find_folder::Search;
use piston_window::*;

fn main() {
    // create a new window
    let mut window: PistonWindow = WindowSettings::new("Continuum", [500, 300])
        .build()
        .unwrap();

    // find the assets folder and create the absolute path for the font file
    let assets_folder = Search::ParentsThenKids(1, 1).for_folder("assets").unwrap();
    let ref font_path = assets_folder.join("FiraSans-Regular.ttf");

    // create a new instance for load Glyphs
    let factory = window.factory.clone();
    let mut glyphs = Glyphs::new(font_path, factory, TextureSettings::new()).unwrap();

    // only enable rendering when receiving inputs
    window.set_lazy(true);

    while let Some(event) = window.next() {
        // draw a 2d component
        window.draw_2d(&event, |context, g| {
            // get X and Y in the local coordinates
            let transform = context.transform.trans(10.0, 100.0);

            // clear the previous render
            clear([0.0, 0.0, 0.0, 1.0], g);

            // render text
            text::Text::new_color([0.0, 1.0, 0.0, 1.0], 32).draw("Hello world!", &mut glyphs, &context.draw_state, transform, g);
        });
    };
}
