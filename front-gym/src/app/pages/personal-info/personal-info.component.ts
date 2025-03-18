import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../componentes/header/header.component";
import { FooterComponent } from "../../componentes/footer/footer.component";
import { ActivatedRoute } from '@angular/router';
import { PersonalService } from '../../services/personal.service';
import { AuthService } from '../../services/auth.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-personal-info',
  imports: [HeaderComponent, FooterComponent],
  templateUrl: './personal-info.component.html',
  styleUrl: './personal-info.component.css'
})
export class PersonalInfoComponent implements OnInit {
  id!: number;
  personalInfo!: any;


  constructor(private route: ActivatedRoute, private personalService: PersonalService, private authService: AuthService) {
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
  }

  getPersonalInfo() {
    this.personalService.getPersonalId(this.authService.getToken(), this.id).subscribe({
      next: data => {
        this.personalInfo = data;
      }, error(error) {
        swal.fire({
          icon: 'error',
          text: `Error: ${error.error.mensaje}`
        });
        setInterval(() => {
          window.location.href = "/";
        }, 500);
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

}
