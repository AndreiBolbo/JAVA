package com.example.guiex1.services;



import com.example.guiex1.domain.Prietenie;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.userDto;
import com.example.guiex1.repository.Repository;
import com.example.guiex1.repository.dbrepo.FriendshipDbRepository;
import com.example.guiex1.utils.events.ChangeEventType;
import com.example.guiex1.utils.events.UtilizatorEntityChangeEvent;
import com.example.guiex1.utils.observer.Observable;
import com.example.guiex1.utils.observer.Observer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UtilizatorService implements Observable<UtilizatorEntityChangeEvent> {
    private Repository<Long, Utilizator> repo;
    private FriendshipDbRepository repo1;
    private List<Observer<UtilizatorEntityChangeEvent>> observers=new ArrayList<>();

    public UtilizatorService(Repository<Long, Utilizator> repo, FriendshipDbRepository repo1) {


        this.repo = repo;
        this.repo1 = repo1;
    }

    public Utilizator login(String username, String password) {
        Iterator var3 = this.getAll().iterator();

        Utilizator utilizator;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            utilizator = (Utilizator)var3.next();
        } while(!Objects.equals(utilizator.getUsername(), username) || !Objects.equals(password, utilizator.getPassword()));

        return utilizator;
    }
    public Utilizator getUserById(Long id){
        for (Utilizator utilizator : this.getAll()){
            if (Objects.equals(utilizator.getId(), id))
                return utilizator;
        }
        return null;
    }
    public Utilizator getUserByUsername(String userName) {
        Iterator var2 = this.getAll().iterator();

        Utilizator utilizator;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            utilizator = (Utilizator)var2.next();
        } while(!Objects.equals(utilizator.getUsername(), userName));

        return utilizator;
    }
    public Utilizator addUtilizator(Utilizator user) {
        if(repo.save(user).isEmpty()){
            UtilizatorEntityChangeEvent event = new UtilizatorEntityChangeEvent(ChangeEventType.ADD, user);
            notifyObservers(event);
            return null;
        }
        return user;
    }
    public Prietenie updatePrietenie(Long id1,Long id2) throws SQLException {
        if(repo1.update(id1,id2).isEmpty()){
            UtilizatorEntityChangeEvent event = new UtilizatorEntityChangeEvent(ChangeEventType.ADD, null);
            notifyObservers(event);
            return null;
        }
       return null;
    }
    public Utilizator deleteUtilizator(Long id) throws SQLException {
        Optional<Utilizator> user = repo.delete(id);
        if (user.isEmpty()) {
            Utilizator usr = new Utilizator("AA", "BB","1","sa");
            notifyObservers(new UtilizatorEntityChangeEvent(ChangeEventType.DELETE, usr));
            //return user.get();}

        }
        return null;
    }
    public Prietenie deletePrietenie(Long id1,Long id2) throws SQLException {
        Optional<Prietenie> prietenie = repo1.delete(id1,id2);
        if (prietenie.isEmpty()) {
           // Prietenie usr = new Prietenie(1L, 3L, Date.valueOf("1988-09-29"),"sa");
            notifyObservers(new UtilizatorEntityChangeEvent(ChangeEventType.DELETE, null));
            //return user.get();}

        }
        return null;
    }
    public Prietenie add_friend(Long id1,Long id2) throws SQLException {
        Prietenie prietenie = new Prietenie(id1,id2, Date.valueOf(LocalDate.now().toString()),"requested");
        Optional<Prietenie> prietenieo = repo1.save(prietenie);
        if (prietenieo.isEmpty()) {
            // Prietenie usr = new Prietenie(1L, 3L, Date.valueOf("1988-09-29"),"sa");
            notifyObservers(new UtilizatorEntityChangeEvent(ChangeEventType.ADD, null));
            //return user.get();}

        }
        return null;
    }
    public Iterable<Utilizator> getAll(){
        return repo.findAll();
    }
    public Iterable<Prietenie> getAllPrietenii(){
        return repo1.findAll();
    }



    @Override
    public void addObserver(Observer<UtilizatorEntityChangeEvent> e) {
        observers.add(e);

    }
    public ArrayList<Utilizator> AllUsers(Long id) throws SQLException {
        Iterable<Utilizator> users = this.getAll();
        ArrayList<Utilizator> user = new ArrayList<Utilizator>();
        for (Utilizator utilizator : users){
//            if (Objects.equals(prieten.getId1(), id) && Objects.equals(prieten.getStatus(), "requested"))
//                userdto.add(new userDto(this.getUserById(prieten.getId2()).getUsername(),prieten.getDate(),prieten.getStatus()));
            if (repo1.findOne(id,utilizator.getId()).isEmpty() && repo1.findOne(utilizator.getId(),id).isEmpty() &&
                    !Objects.equals(utilizator.getId(), id))
                user.add(utilizator);

        }
        return  user;
    }
    public void DeleteAllFriends(Long id) throws SQLException {
        Iterable<Prietenie> prietenii = this.getAllPrietenii();
        for (Prietenie prietenie: prietenii){
            if(Objects.equals(prietenie.getId1(), id)  )
                repo1.delete(id,prietenie.getId2());
            if(Objects.equals(prietenie.getId2(), id))
                repo1.delete(prietenie.getId1(),id);

        }
    }
    public ArrayList<userDto> all_requested(Long id)
    {
        Iterable<Prietenie> prietenie = this.getAllPrietenii();
        ArrayList<userDto> userdto = new ArrayList<userDto>();
        for (Prietenie prieten : prietenie){
//            if (Objects.equals(prieten.getId1(), id) && Objects.equals(prieten.getStatus(), "requested"))
//                userdto.add(new userDto(this.getUserById(prieten.getId2()).getUsername(),prieten.getDate(),prieten.getStatus()));
            if (Objects.equals(prieten.getId2(), id) && Objects.equals(prieten.getStatus(), "requested"))
                userdto.add(new userDto(this.getUserById(prieten.getId1()).getUsername(),prieten.getDate(),prieten.getStatus()));
        }
        return  userdto;
    }
    public ArrayList<userDto> all_confirmed(Long id)
    {
        Iterable<Prietenie> prietenie = this.getAllPrietenii();
        ArrayList<userDto> userdto = new ArrayList<userDto>();
        for (Prietenie prieten : prietenie){
            if (Objects.equals(prieten.getId1(), id) && Objects.equals(prieten.getStatus(), "confirmed"))
                userdto.add(new userDto(this.getUserById(prieten.getId2()).getUsername(),prieten.getDate(),prieten.getStatus()));
            if (Objects.equals(prieten.getId2(), id) && Objects.equals(prieten.getStatus(), "confirmed"))
                userdto.add(new userDto(this.getUserById(prieten.getId1()).getUsername(),prieten.getDate(),prieten.getStatus()));
        }
        return  userdto;
    }

    @Override
    public void removeObserver(Observer<UtilizatorEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorEntityChangeEvent t) {

        observers.stream().forEach(x->x.update(t));
    }


}
