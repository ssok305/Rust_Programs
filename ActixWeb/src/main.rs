mod api;
mod model;
mod repository;
use api::task::{
    get_task,
    submit_task,
    start_task,
    complete_task,
    pause_task,
    fail_task

};
// Uses Actix Web Framework to help set up the website
use actix_web::{HttpServer, App, web::Data, middleware::Logger};

use aws_config::meta::region::RegionProviderChain;
// Database using is a repository from the local file, located in /home/ssok1/ActixWeb/repository
use repository::ddb::{DDBRepository};

//Using the Actix Web attributes for the entire Main.rs file

#[actix_web::main]

// Main Function
async fn main() -> std::io::Result<()>  {
    std::env::set_var("RUST_LOG", "debug");
    std::env::set_var("RUST_BACKTRACE", "1");
    env_logger::init();

    let config = aws_config:: load_from_env().await;

    // Creating a new Http Server and having our database be connected
    HttpServer::new(move ||{
        let ddb_repo: DDBRepository = DDBRepository::init(
            String::from("task"),
            config.clone(),
        );
        let ddb_data = Data::new(ddb_repo);

        //Logger is used to help send messages to the user if any error, warnings, info, debug, trace
        let logger = Logger::default();

        //Creates a new instance and while using wrap, binds the other functions together and run.
        App::new()
            .wrap(logger)
            .app_data(ddb_data)
            .service(get_task)
            .service(submit_task)
            .service(start_task)
            .service(fail_task)
            .service(pause_task)
            .service(complete_task)
    });
    .bind(("127.0.0.1",80))?
    .run()
    .await

}
