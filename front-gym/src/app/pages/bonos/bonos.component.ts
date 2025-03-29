import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { HeaderComponent } from "../../componentes/header/header.component";
import { FooterComponent } from "../../componentes/footer/footer.component";
import { PersonalService } from '../../services/personal.service';
import { AuthService } from '../../services/auth.service';
import swal from 'sweetalert2';
import { BonosYdescuentosService } from '../../services/bonos-descuentos.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-bonos',
  imports: [HeaderComponent, FooterComponent, FormsModule],
  templateUrl: './bonos.component.html',
  styleUrl: './bonos.component.css'
})
export class BonosComponent implements OnInit {
  personal!: Array<any>;
  bonos!: Array<any>;
  modelo!: any;
  bonoSeleccionado: number = 0;
  @ViewChild("myModalConf", { static: false }) myModalConf!: TemplateRef<any>


  constructor(private personalService: PersonalService, private authService: AuthService,
    private bonosService: BonosYdescuentosService, private modalService: NgbModal) {
    this.authService.methodAuth();
    this.modelo = {
      nombre: "",
      monto: ""
    }
  }

  ngOnInit(): void {
    this.getPersonal();
    this.getBonos();
  }

  getPersonal() {
    this.personalService.getListarPersonal(this.authService.getToken()).subscribe({
      next: data => {
        this.personal = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          title: 'Opss',
          text: "Ocurrio un error al cargar el personal"
        });
      }
    });
  }


  getBonos() {
    this.bonosService.getBonos(this.authService.getToken()).subscribe({
      next: data => {
        this.bonos = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          title: "Opss",
          text: "Ocurrio un error: " + error
        });
      }
    });
  }

  ventanaModal() {
    this.modalService.open(this.myModalConf, {
      size: 'lg',
      backdrop: false,  // Elimina el sombreado de fondo
    });
  }

  RegistrarBono() {
    console.log(this.modelo)
    this.bonosService.registraBono(this.modelo, this.authService.getToken()).subscribe({
      next: data => {
        swal.fire({
          icon: 'success',
          title: "Registrado",
          text: "Se registro correctamente el bono:"
        });
        /*setInterval(() => {
          window.location.reload();
        }, 1000);*/
      }, error(error) {
        console.log(error)
        swal.fire({
          icon: 'error',
          title: "Opss",
          text: "Ocurrio un error: " + error.error.mensaje
        });
      }
    });
  }

  BonoEmpleados() {
    if (this.bonoSeleccionado > 0) {
      swal.fire({
        title: '¿Seguro que quieres Asignar el bono?',
        text: 'Esta accion no se puede modificar. ¿Estás seguro de que deseas continuar?',
        icon: 'warning',
        showCancelButton: true,
        //confirmButtonColor: "#12E603",
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'Sí, Asignar a todos',
        cancelButtonColor: 'red',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.bonosService.bonoEmpleados(this.bonoSeleccionado, this.authService.getToken()).subscribe({
            next: data => {
              swal.fire({
                title: "Registrado",
                text: "Se Asigno el bono a todo el Personal:",
                icon: "success"
              });
              setInterval(() => {
                window.location.reload();
              }, 1000);
            }, error(error) {
              swal.fire({
                icon: 'error',
                title: "Opss",
                text: "Ocurrio un error al asignar bono"
              });
            }
          });
        }
        return;
      });
    }
    else {
      swal.fire({
        title: "No has seleccionado un bono",
        icon: "error"
      });
    }
  }

  BonoIndividual(idUser: number) {
    if (idUser > 0 && this.bonoSeleccionado > 0) {
      swal.fire({
        title: '¿Seguro que quieres Asignar el bono al personal seleccionado?',
        text: '¡Esta accion no se puede revertir!',
        icon: 'warning',
        showCancelButton: true,
        //confirmButtonColor: "#12E603",
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'Sí, Asignar bono individual',
        cancelButtonColor: 'red',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.bonosService.bonoIndividual({ idBono: this.bonoSeleccionado, idPersonal: idUser }, this.authService.getToken()).subscribe({
            next: data => {
              console.log(data)
              swal.fire({
                title: "Bono Asignado",
                text: data.mensaje,
                icon: "success"
              });
              setInterval(() => {
                window.location.reload();
              }, 1200);
            }, error(error) {
              console.log(error)
              swal.fire({
                icon: 'error',
                title: "Opss",
                text: "Ocurrio un error al asignar bono"
              });
            }
          });
        }
        return;
      });
    }
    else {
      swal.fire({
        title: "Selecciona un Bono",
        icon: "error"
      });
    }
  }

}
