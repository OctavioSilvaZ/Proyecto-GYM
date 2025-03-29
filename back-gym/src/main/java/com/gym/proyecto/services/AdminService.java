package com.gym.proyecto.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gym.proyecto.DTO.PersonalDatosResponse;
import com.gym.proyecto.DTO.PersonalListResponse;
import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.models.PersonalBonosModel;
import com.gym.proyecto.models.PersonalModel;

@Service
public class AdminService {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private EstadoPersonalService estadoPersonalService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private JornadaService jornadaService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public PersonalDatosResponse personalFound(PersonalModel personal) {
        // Listas para guardar los datos de los bonos
        Integer bonoTotal=0;
        List<Integer> bonosMonto = new ArrayList<>();
        List<String> bonosNombres = new ArrayList<>();
        if (personal.getBonosAsignados() != null) {
            for (PersonalBonosModel bono : personal.getBonosAsignados()) {
                bonosMonto.add(bono.getBono().getMonto());
                bonosNombres.add(bono.getBono().getNombre());
                bonoTotal += bono.getBono().getMonto();
            }
        } else {
            bonosMonto = null;
            bonosNombres = null;
        }

        PersonalDatosResponse personalResponse = new PersonalDatosResponse(personal.getId(), personal.getNombre(),
                personal.getApePaterno(), personal.getApeMaterno(),
                personal.getDireccion(), personal.getTelefono(), personal.getCorreo(),
                personal.getEstado().getNombre(), personal.getEstado().getId(), personal.getIne(),
                personal.getHorario().getTipo(), personal.getHorario().getId(), personal.getJornada().getTipo(),
                personal.getJornada().getId(), personal.getHoras_faltantes(),
                personal.getHoras_extra(), personal.getDias_faltantes(),
                bonosMonto,bonoTotal, bonosNombres, personal.getNoCuenta(),
                personal.getFecha_pago());

        return personalResponse;
    }

    public List<PersonalListResponse> personalAll(List<PersonalModel> personal) {

        List<PersonalListResponse> lista = new ArrayList<>();

        personal.forEach((datos) -> {

            // Se recorre para buscar todos los bonos que tenga cada personal
            List<Integer> bonosMonto = new ArrayList<>();
            List<String> bonosNombres = new ArrayList<>();
            if (datos.getBonosAsignados() != null) {
                for (PersonalBonosModel bono : datos.getBonosAsignados()) {
                    bonosMonto.add(bono.getBono().getMonto());
                    bonosNombres.add(bono.getBono().getNombre());
                }
            } else {
                bonosMonto = null;
                bonosNombres = null;
            }

            // Oculta el rol personal para no mostrarlo
            if (datos.getRolPersonal().getRolId().getId() != 1) {
                lista.add(new PersonalListResponse(datos.getId(), datos.getNombre(), datos.getApePaterno(),
                        datos.getApeMaterno(), datos.getEstado().getNombre(), datos.getHorario().getTipo(),
                        datos.getJornada().getTipo(), datos.getFecha_pago(),
                        bonosMonto, bonosNombres));
            }
        });
        return lista;
    }

    public PersonalModel personalUpdate(RegisterPersonalRequest request, long id,
            MultipartFile fotoImg, MultipartFile ineImg) throws IOException {

        PersonalModel personal = this.personalService.buscarPorId(id);

        if (personal != null) {
            personal.setNombre(request.getNombre());
            personal.setApePaterno(request.getApePaterno());
            personal.setApeMaterno(request.getApeMaterno());
            personal.setDireccion(request.getDireccion());
            personal.setTelefono(request.getTelefono());
            personal.setCorreo(request.getCorreo());
            personal.setEstado(this.estadoPersonalService.buscarId(request.getEstado()));
            personal.setHorario(this.horarioService.buscarId(request.getHorario()));
            personal.setJornada(this.jornadaService.buscarId(request.getJornada()));
            personal.setHoras_faltantes(request.getHorasFaltantes());
            personal.setHoras_extra(request.getHorasExtra());
            personal.setDias_faltantes(request.getDiasFaltantes());
            personal.setNoCuenta(request.getNoCuenta());

            if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
                personal.setPassword(this.passwordEncoder.encode(request.getPassword()));
            }

            String nombre = personal.getNombre().substring(0, 3); // Primeras 3 letras del nombre
            String apePaterno = personal.getApePaterno().substring(0, 4);
            String apeMaterno = personal.getApeMaterno().substring(0, 4);
            String foto = null;
            String ine = null;

            foto = personal.getFoto();
            ine = personal.getIne();

            if (fotoImg != null && !fotoImg.isEmpty()) {
                if (foto == null) {
                    foto = nombre + "_" + apePaterno + "_" + apeMaterno + ".png";
                }
                this.personalService.guardarArchivo(fotoImg, 0, foto);
            }

            if (ineImg != null && !ineImg.isEmpty()) {
                if (ine == null) {
                    ine = nombre + "_" + apePaterno + "_" + apeMaterno + "_Ine" + ".pdf";
                }
                this.personalService.guardarArchivo(ineImg, 1, ine);
            }

            personal.setFoto(foto);
            personal.setIne(ine);

            this.personalService.guardar(personal, 2);
            return personal;
        } else {
            return null;
        }
    }
}
