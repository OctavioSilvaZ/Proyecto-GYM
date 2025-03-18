import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { PersonalInfoComponent } from './pages/personal-info/personal-info.component';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { UsuariosInfoComponent } from './pages/usuarios-info/usuarios-info.component';
import { MontosComponent } from './pages/montos/montos.component';
import { DescuentosComponent } from './pages/descuentos/descuentos.component';
import { ErrorComponent } from './pages/error/error.component';

export const routes: Routes = [
    { path: "", component: HomeComponent },
    { path: "login", component: LoginComponent },
    { path: "personal-info/:id", component: PersonalInfoComponent },
    { path: "usuarios", component: UsuariosComponent },
    { path: "usuarios-info", component: UsuariosInfoComponent },
    { path: "montos", component: MontosComponent },
    { path: "descuentos", component: DescuentosComponent },
    { path: "**", component: ErrorComponent }

];
