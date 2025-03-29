import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { HeaderComponent } from "../../componentes/header/header.component";
import { FooterComponent } from "../../componentes/footer/footer.component";
import { ActivatedRoute } from '@angular/router';
import { PersonalService } from '../../services/personal.service';
import { AuthService } from '../../services/auth.service';
import swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-personal-info',
  imports: [HeaderComponent, FooterComponent, FormsModule],
  templateUrl: './personal-info.component.html',
  styleUrl: './personal-info.component.css'
})
export class PersonalInfoComponent implements OnInit {
  id!: number;
  personalInfo!: any;
  isEditing = false;
  estados: any;
  horarios: any;
  jornadas: any;
  passwordConfirm!: String;
  imagen: any;
  ine: any;

  @ViewChild("myModalConf", { static: false }) myModalConf!: TemplateRef<any>;
  modalTitle!: string;

  constructor(private sanitizer: DomSanitizer, private route: ActivatedRoute, private personalService: PersonalService, private authService: AuthService) {
    this.personalInfo = {
      nombre: "",
      apePaterno: "",
      apeMaterno: "",
      correo: "",
      estado: "",
      horario: "",
      jornada: "",
      direccion: "",
      telefono: "",
      horas_Faltantes: "",
      horas_Extra: "",
      dias_Faltantes: "",
      bono: "",
      ine: "",
      foto: "",
      fecha_pago: "",

    }
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.id = +id; // Convertimos el valor a número
    } else {
      console.error('ID no encontrado en la URL');
    }
    this.getPersonalInfo();
    this.getEstados();
    this.getHorarios();
    this.getJornada();
    this.getFoto();
  }

  confirmarConstrasenas(): boolean {
    return (
      this.personalInfo.password !== '' &&
      this.personalInfo.passwordConfirm !== '' &&
      this.personalInfo.password !== this.passwordConfirm
    );
  }


  checkFiles(event: any, tipo: string): void {
    // Obtenemos el archivo del input
    const file: File = event.target.files[0];
    if (file) {
      // Si el archivo no está vacío, lo asignamos a la variable correspondiente
      if (tipo === 'foto') {
        this.personalInfo.foto = file;
      } else if (tipo === 'ine') {
        this.personalInfo.ine = file;
      }
    } else {
      // Si el archivo está vacío (no se ha seleccionado ningún archivo), asignamos null
      if (tipo === 'foto') {
        this.personalInfo.foto = null;
      } else if (tipo === 'ine') {
        this.personalInfo.ine = null;
      }
    }
  }

  //Convierte el archivo
  createImageFromBlob(archivo: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imagen = reader.result;
    }, false);

    if (archivo != null) {
      reader.readAsDataURL(archivo);
    }
  }

  getFoto() {
    this.personalService.fotoPersonal(this.authService.getToken(), this.id).subscribe({
      next: data => {
        this.createImageFromBlob(data);
      }, error(error) {
        console.log("no se encontro foto");
      }
    });
  }

  getIne() {
    this.personalService.inePersonal(this.authService.getToken(), this.id).subscribe({
      next: data => {
        this.ine = URL.createObjectURL(data);
        window.open(this.ine);
      }, error(error) {
        console.log(error);
      }
    });
  }

  getPersonalInfo() {
    this.personalService.getPersonalId(this.authService.getToken(), this.id).subscribe({
      next: data => {
        console.log(data);
        this.personalInfo = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          text: `Error: ${error.error.mensaje}`
        });
        setInterval(() => {
          window.location.href = "/";
        }, 600);
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

  deletePersonal() {
    swal.fire({
      title: '¿Seguro que quieres eliminar a este Personal?',
      text: '¡Se borrara de forma permanente!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, Eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.personalService.deletePersonal(this.authService.getToken(), this.id).subscribe({
          next: data => {
            swal.fire({
              position: "center",
              icon: "success",
              title: "Personal Eliminado",
              showConfirmButton: false,
              timer: 1000
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
        });
      }
      return;
    });
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  confirm(accion: any) {
    console.log(accion)
    if (accion === 'Guardar') {
      swal.fire({
        title: '¿Estos cambios son correctos?',
        text: 'Esta accion no se puede revertir',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'Sí, Guardar',
        cancelButtonText: 'No, Modificar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.sendForm();
          this.isEditing = false;
        }
        return;

      });

    } else {
      this.isEditing = false;
    }
  }


  sendForm() {
    // Crear el FormData
    const formData = new FormData();

    // Agregar los campos 
    formData.append('nombre', this.personalInfo.nombre);
    formData.append('apePaterno', this.personalInfo.apePaterno);
    formData.append('apeMaterno', this.personalInfo.apeMaterno);
    formData.append('direccion', this.personalInfo.direccion);
    formData.append('telefono', this.personalInfo.telefono.toString());
    formData.append('correo', this.personalInfo.correo);
    formData.append('password', this.personalInfo.password);
    formData.append('estado', this.personalInfo.estado_id);
    formData.append('horario', this.personalInfo.horario_id);
    formData.append('jornada', this.personalInfo.jornada_id);
    formData.append('horasFaltantes', this.personalInfo.horas_Faltantes);
    formData.append('horasExtra', this.personalInfo.horas_Extra);
    formData.append('diasFaltantes', this.personalInfo.dias_Faltantes);

    // Archivos
    if (this.personalInfo.foto) {
      formData.append('foto', this.personalInfo.foto);
    }

    if (this.personalInfo.ine) {
      formData.append('ine', this.personalInfo.ine);
    }

    this.personalService.updatePersonal(formData, this.authService.getToken(), this.id).subscribe(
      {
        next: data => {
          swal.fire({
            icon: 'success',
            title: 'OK',
            text: 'Se Actualizo correctamente el personal'
          });
          setInterval(() => {
            window.location.reload();
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
