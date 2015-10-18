import qajax from 'qajax';

export function signin() {

}

export function signup() {
  return qajax.getJSON('/bo/assets/data/profilsExternes.json');
}