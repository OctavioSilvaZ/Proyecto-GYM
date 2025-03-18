import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FooterComponent } from "../../componentes/footer/footer.component";
import { HeaderComponent } from "../../componentes/header/header.component";
import { AuthService } from '../../services/auth.service';
import { PersonalService } from '../../services/personal.service';
import swal from 'sweetalert2';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [FooterComponent, HeaderComponent, FormsModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  personal!: Array<any>;
  estados!: Array<any>;
  horarios!: Array<any>;
  jornadas!: Array<any>;
  modelo: any;
  passwordConfirm!: string;

  @ViewChild("myModalConf", { static: false }) myModalConf!: TemplateRef<any>;
  modalTitle!: string;


  constructor(private authService: AuthService, private personalService: PersonalService,
    private modalService: NgbModal) {
    this.authService.methodAuth();
    this.modelo = {
      nombre: "",
      apePaterno: "",
      apeMaterno: "",
      direccion: "",
      telefono: "",
      correo: "",
      password: "",
      estado: "",
      horario: "",
      jornada: "",
      foto: null,
      ine: null
    }

  }

  ngOnInit(): void {
    this.getPersonal();
    this.getEstados();
    this.getHorarios();
    this.getJornada();
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

  getEstados() {
    this.personalService.estadosPersonal(this.authService.getToken()).subscribe({
      next: data => {
        this.estados = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          title: 'Opss',
          text: "Ocurrio un error al cargar los estados"
        });
      }
    });
  }

  getHorarios() {
    this.personalService.horariosPersonal(this.authService.getToken()).subscribe({
      next: data => {
        this.horarios = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          title: 'Opss',
          text: "Ocurrio un error al cargar los horarios"
        });
      }
    });
  }


  getJornada() {
    this.personalService.jornadaPersonal(this.authService.getToken()).subscribe({
      next: data => {
        this.jornadas = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          title: 'Opss',
          text: "Ocurrio un error al cargar las jornadas"
        });
      }
    });
  }


  confirmarConstrasenas(): boolean {
    return (
      this.modelo.password !== '' &&
      this.modelo.passwordConfirm !== '' &&
      this.modelo.password !== this.passwordConfirm
    );
  }

  checkFiles(event: any, tipo: string): void {
    // Obtenemos el archivo del input
    const file: File = event.target.files[0];
    if (file) {
      // Si el archivo no está vacío, lo asignamos a la variable correspondiente
      if (tipo === 'foto') {
        this.modelo.foto = file;
      } else if (tipo === 'ine') {
        this.modelo.ine = file;
      }
    } else {
      // Si el archivo está vacío (no se ha seleccionado ningún archivo), asignamos null
      if (tipo === 'foto') {
        this.modelo.foto = null;
      } else if (tipo === 'ine') {
        this.modelo.ine = null;
      }
    }


  }

  registrar() {
    this.modalService.open(this.myModalConf, {
      size: 'lg',
      backdrop: false,  // Elimina el sombreado de fondo
      keyboard: true     // Permite cerrar el modal con la tecla ESC
    });
    this.modelo = {
      nombre: "",
      apePaterno: "",
      apeMaterno: "",
      direccion: "",
      telefono: "",
      correo: "",
      password: "",
      estado: "",
      horario: "",
      jornada: "",
      foto: null,
      ine: null
    }
    this.passwordConfirm = '';

    this.modalTitle = "Registrar";
  }


  enviar() {
    if (this.modalTitle == 'Registrar') {

      // Verificar si la foto es nula
      if (!this.modelo.foto) {
        swal.fire({
          title: '¿Quieres enviar la foto como null?',
          text: 'No has seleccionado una foto. ¿Estás seguro de que deseas continuar?',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonText: 'Sí, enviar como null',
          cancelButtonText: 'No, elegir otra foto'
        }).then((result) => {
          if (!result.isConfirmed) {
            return; // Si el usuario no confirma, no enviamos el formulario
          }

          // Verificar si el INE es nulo
          if (!this.modelo.ine) {
            swal.fire({
              title: '¿Quieres enviar el INE como null?',
              text: 'No has seleccionado un INE. ¿Estás seguro de que deseas continuar?',
              icon: 'warning',
              showCancelButton: true,
              confirmButtonText: 'Sí, enviar como null',
              cancelButtonText: 'No, elegir otro INE'
            }).then((result) => {
              if (!result.isConfirmed) {
                return; // Si el usuario no confirma, no enviamos el formulario
              }

              // Si ambos archivos son nulos o el usuario confirma enviarlos como null
              this.sendForm();
            });
          } else {
            // Si el INE está presente, enviamos el formulario
            this.sendForm();
          }
        });

      } else {
        // Si la foto no es nula, verificamos el INE
        if (!this.modelo.ine) {
          swal.fire({
            title: '¿Quieres enviar el INE como null?',
            text: 'No has seleccionado un INE. ¿Estás seguro de que deseas continuar?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, enviar como null',
            cancelButtonText: 'No, elegir otro INE'
          }).then((result) => {
            if (!result.isConfirmed) {
              return; // Si el usuario no confirma, no enviamos el formulario
            }

            // Si el INE es nulo o el usuario lo confirma
            this.sendForm();
          });
        } else {
          // Si ambos archivos están presentes
          this.sendForm();
        }
      }
    }
  }

  sendForm() {
    // Crear el FormData aquí
    const formData = new FormData();

    // Agregar los campos uno por uno
    formData.append('nombre', this.modelo.nombre);
    formData.append('apePaterno', this.modelo.apePaterno);
    formData.append('apeMaterno', this.modelo.apeMaterno);
    formData.append('direccion', this.modelo.direccion);
    formData.append('telefono', this.modelo.telefono.toString());
    formData.append('correo', this.modelo.correo);
    formData.append('password', this.modelo.password);
    formData.append('estado', this.modelo.estado);
    formData.append('horario', this.modelo.horario);
    formData.append('jornada', this.modelo.jornada);

    // Archivos
    if (this.modelo.foto) {
      formData.append('foto', this.modelo.foto);
    }

    if (this.modelo.ine) {
      formData.append('ine', this.modelo.ine);
    }

    // Aquí llamas al servicio, pero ahora envías el FormData directamente
    this.personalService.registerPersonal(formData, this.authService.getToken()).subscribe(
      {
        next: data => {
          swal.fire({
            icon: 'success',
            title: 'OK',
            text: 'Se registró correctamente el personal'
          });
          setInterval(() => {
            window.location.href = "/";
          }, 1000);
        }, error(error) {
          console.log(error)
          swal.fire({
            icon: 'error',
            title: 'Error',
            text: `Error: ${error.error.mensaje}`
          });
        }
      }
    );
  }


}
